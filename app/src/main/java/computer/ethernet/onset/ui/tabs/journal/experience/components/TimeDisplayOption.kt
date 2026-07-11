package computer.ethernet.onset.ui.tabs.journal.experience.components

enum class SavedTimeDisplayOption {
    AUTO {
        override val text = "Auto"
    }, RELATIVE_TO_NOW {
        override val text = "Time relative to now"
    }, RELATIVE_TO_START {
        override val text = "Time relative to start"
    }, TIME_BETWEEN {
        override val text = "Time between"
    }, REGULAR {
        override val text = "Regular time"
    };

    abstract val text: String
}


enum class TimeDisplayOption {
    RELATIVE_TO_NOW, RELATIVE_TO_START, TIME_BETWEEN, REGULAR;
}