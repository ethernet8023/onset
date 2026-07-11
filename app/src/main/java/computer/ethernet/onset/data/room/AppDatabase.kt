package computer.ethernet.onset.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import computer.ethernet.onset.data.room.experiences.ExperienceDao
import computer.ethernet.onset.data.room.experiences.entities.CustomSubstance
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.InstantConverter
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.data.room.experiences.entities.TimedNote

@TypeConverters(InstantConverter::class)
@Database(
    version = 7,
    entities = [Experience::class, Ingestion::class, SubstanceCompanion::class, CustomSubstance::class, ShulginRating::class, TimedNote::class, CustomUnit::class],
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3),
        AutoMigration (from = 3, to = 4),
        AutoMigration (from = 4, to = 5),
        AutoMigration (from = 5, to = 6),
        AutoMigration (from = 6, to = 7),
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun experienceDao(): ExperienceDao
}