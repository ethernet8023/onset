# AGENTS.md — Onset Android

## What is this

Onset (formerly PsychonautWiki Journal / PsyLog) is an Android harm-reduction app for
tracking recreational drug experiences. Users log ingestions, view substance
timelines, check interactions, and get safer-use info. All data stays on-device.

- **Package**: `computer.ethernet.onset`
- **Origin**: Fork of `isaakhanimann/psychonautwiki-journal-android` (via PsyLog fork by zotan)
- **Remote**: `github.com/ethernet8023/onset`
- **License**: GPL-3.0 (see `COPYING`)
- **Published on**: GitHub releases

## Tech stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material 3 |
| Language | Kotlin 2.0.20, JVM 17 |
| DI | Hilt (Dagger) with kapt |
| Database | Room v7 (auto-migrations), schema in `app/schemas/` |
| Navigation | Navigation Compose with serializable route objects |
| Serialization | kotlinx.serialization (Substances.json, journal export) |
| Coroutines | kotlinx.coroutines 1.9.0 |
| Widgets | Glance (appwidget + material3) |
| Charts | Custom Canvas drawables (no charting library) |
| Build | Gradle with version catalog (`gradle/libs.versions.toml`) |
| Nix | `flake.nix` provides dev + CI shells via android-nixpkgs |

## Project structure

```
app/src/main/java/computer/ethernet/onset/
├── MainActivity.kt              # Entry point — splash, edge-to-edge, notification perm
├── di/                           # Hilt modules + Application class
│   ├── OnsetApplication.kt       # @HiltAndroidApp, resumes LiveActivityService on launch
│   ├── AppModule.kt             # Provides Room DB, DAO, DataStore
│   └── RepositoryModule.kt       # Binds repository interfaces to implementations
├── data/
│   ├── room/
│   │   ├── AppDatabase.kt        # Room database (v7), 7 entities, auto-migrations
│   │   ├── experiences/
│   │   │   ├── ExperienceDao.kt  # All DAO queries
│   │   │   ├── ExperienceRepository.kt  # Thin wrapper over DAO
│   │   │   ├── entities/         # Room entities: Experience, Ingestion, etc.
│   │   │   └── relations/        # @Relation POJOs (e.g. ExperienceWithIngestions)
│   │   └── substances/
│       ├── classes/              # Domain models: Substance, Category, Roa, etc.
│       │   └── roa/              # RoaDose, RoaDuration, IngestionPhase, DurationRange
│       ├── parse/                # SubstanceParser — reads Substances.json
│       └── repositories/         # SubstanceRepository (loads from assets), SearchRepository
├── service/                      # Foreground service + widget data
│   ├── LiveActivityService.kt    # Foreground service: polls active experience, updates notification
│   ├── LiveActivityManager.kt    # Start/stop helper for the service
│   ├── LiveActivityDataProvider.kt  # Builds LiveActivityData from repos
│   └── LiveActivityData.kt       # Data classes for notification/widget display
├── widget/
│   ├── TimelineWidget.kt         # Glance widget showing active experience timeline
│   ├── TimelineWidgetReceiver.kt # AppWidget receiver
│   └── WidgetEntryPoint.kt       # Hilt EntryPoint for widget (can't use @Inject in Glance)
└── ui/
    ├── Constants.kt              # App-wide constants (disclaimers, URLs, VERSION_NAME)
    ├── theme/                    # Material 3 theme (OnsetTheme) + colors
    ├── main/                     # MainScreen, navigation, nav graphs
    │   └── navigation/graphs/    # Per-tab nav graphs (journal, stats, search, safer, settings)
    ├── utils/                    # Date/time helpers, keyboard utils
    └── tabs/
        ├── journal/              # Experience tracking, ingestion logging, timeline rendering
        │   ├── JournalScreen.kt  # Journal tab — list of experiences
        │   ├── experience/       # Single experience view, edit, ratings, timed notes
        │   │   └── timeline/      # Custom Canvas timeline drawables + bitmap renderer
        │   ├── addingestion/     # Multi-step add-ingestion flow (search → interactions → route → dose → time)
        │   └── calendar/         # Calendar view of experiences
        ├── search/               # Substance search + detail screens
        │   └── substance/       # Substance detail (dose, duration, interactions, tolerance)
        ├── safer/                # Static harm-reduction info screens
        ├── stats/                # Usage statistics + bar chart
        └── settings/             # Settings, custom units, color preferences, journal export
```

## Key domain concepts

- **Substance**: A drug from `Substances.json` (bundled asset, ~450KB). Has
  categories, ROAs (routes of administration), dose ranges, duration ranges,
  interactions, tolerance info. Parsed once at app start by `SubstanceParser`.

- **Experience**: A user-created log entry grouping one or more ingestions. Has
  title, text, sort date, optional location. Can have Shulgin ratings and
  timed notes attached.

- **Ingestion**: A single dose event — substance, route, dose, time, optional
  end time (for timed-release). Belongs to an Experience. Links to a
  SubstanceCompanion (color + substance name pair).

- **SubstanceCompanion**: Tracks the user's chosen color for each substance.

- **CustomUnit**: User-defined dosing units (e.g. "1 pill = 100mg").

- **CustomSubstance**: User-created substances not in the bundled database.

- **IngestionPhase**: Calculated from ROA duration data — onset, comeup, peak,
  offset, afterglow, ended, unknown. Uses midpoint interpolation of duration
  ranges. See `calculateCurrentPhase()` in `roa/IngestionPhase.kt`.

- **Timeline rendering**: Custom `Drawable` classes on Canvas (no charting
  library). Each drawable type corresponds to available duration phases:
  `FullTimelines`, `OnsetComeupPeakTimeline`, `OnsetTotalTimeline`, etc.
  `TimelineBitmapRenderer` renders the timeline to a Bitmap for notifications/widgets.

## Build system

### Gradle flavors

Two product flavors along `distribution` dimension:
- **play**: Play Store build (`computer.ethernet.onset`)
- **direct**: Direct/F-Droid build (`computer.ethernet.onset.direct`)

### Build commands

```bash
# Enter Nix dev shell (provides Android SDK, JDK 17, Gradle, Kotlin)
nix develop

# Build
gradle assemblePlayRelease     # Play Store release APK
gradle assembleDirectRelease   # Direct release APK
gradle bundlePlayRelease       # Play Store AAB
gradle assembleDebug           # All debug APKs

# Tests (host unit tests only — no instrumented tests in CI)
gradle test
```

### CI (`.github/workflows/build-release.yml`)

Triggers on tag push (`v*.*`) or manual dispatch. Builds with `assembleRelease`,
signs APK, creates GitHub release with the signed APK. Uses JDK 17 (Zulu).

### Room migrations

Database is at version 7 with auto-migrations (1→2→...→7). Schema JSON files
live in `app/schemas/computer.ethernet.onset.data.room.AppDatabase/`. When
adding/changing entities, bump the version in `AppDatabase.kt` and add an
`AutoMigration` entry (or manual migration if complex). Room generates the
migration at compile time via kapt.

## Testing

Unit tests are in `app/src/test/java/computer/ethernet/onset/`. Tests cover:
- `TestParse.kt` — SubstanceParser robustness + extract logic
- `TestDates.kt` — Date utilities, axis label generation, time difference text
- `TestRegex.kt` — Interaction wildcard matching (e.g. "5-MeO-xxT" → "5-MeO-DALT")
- `DoubleReadableExtensionKtTest.kt` — Dose formatting

Note: `TestDates` and `TestParse` have pre-existing failures — locale-dependent
formatting and `org.json.JSONObject not mocked` (Android stub not available in
host unit tests). These are not caused by the fork.

No instrumented tests are configured for CI. Run with `gradle test`.

## Things to know when working in this codebase

1. **Substances.json is a bundled asset** — not fetched at runtime. The full
   substance database ships in the APK at `app/src/main/assets/Substances.json`.
   `SubstanceRepository` loads and parses it once in its `init` block. All
   substance URLs point to `psy.st` (a PsychonautWiki mirror).

2. **Hilt + kapt** — annotation processing via kapt (not KSP). `kapt` must be
   applied last in the plugins block. `correctErrorTypes = true` is set.

3. **Glance widgets can't use @Inject** — they use `EntryPointAccessors` with
   an `@EntryPoint` interface (`WidgetEntryPoint`). Don't try to inject
   directly into `TimelineWidget`.

4. **LiveActivityService is a foreground service** — type `specialUse` with a
   property describing the subtype. It polls every 30s, updates both the
   notification (with custom RemoteViews including a rendered timeline bitmap)
   and the Glance widget. Self-stops when no active experience is found.

5. **"Active experience"** = an experience with ingestions within
   `hourLimitToSeparateIngestions` hours of now. See
   `LiveActivityDataProvider.getCurrentLiveActivityData()`.

6. **Timeline drawables** are in `ui/tabs/journal/experience/timeline/drawables/`.
   They draw on `Canvas` using `Path`, `DrawScope`, etc. The drawable type is
   chosen based on which duration phases are available for the substance's ROA.
   `TimelineBitmapRenderer` renders these to a `Bitmap` for use in notifications
   and widgets.

7. **VERSION_NAME in Constants.kt** must be kept in sync with `versionName` in
   `app/build.gradle.kts`. It's displayed in Settings as "Version $VERSION_NAME".

## Key files for quick reference

| File | Purpose |
|---|---|
| `app/build.gradle.kts` | App module config, deps, flavors, build types |
| `gradle/libs.versions.toml` | Version catalog for all dependencies |
| `flake.nix` | Nix dev + CI shells with Android SDK |
| `app/src/main/AndroidManifest.xml` | Activities, service, widget receiver |
| `data/room/AppDatabase.kt` | Room DB definition, entities, migrations |
| `data/substances/repositories/SubstanceRepository.kt` | Loads + caches Substances.json |
| `service/LiveActivityService.kt` | Foreground service for active experience tracking |
| `service/LiveActivityDataProvider.kt` | Builds display data from repos |
| `ui/main/MainScreen.kt` | App scaffold with NavigationSuiteScaffold |
| `ui/main/navigation/TopLevelRoute.kt` | 5 top-level tabs: Stats, Journal, Drugs, Safer, Settings |
| `ui/tabs/journal/experience/timeline/drawables/` | Canvas timeline rendering |
| `app/src/main/assets/Substances.json` | Bundled substance database (~450KB) |
