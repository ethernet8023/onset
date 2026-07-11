package computer.ethernet.onset.data.room.experiences.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = StomachFullnessSerializer::class)
enum class StomachFullness {
    EMPTY {
        override val text = "Empty"
        override val serialized = "EMPTY"
        override val onsetDelayForOralInHours: Double = 0.0
    },
    QUARTER_FULL {
        override val text = "Quarter full"
        override val serialized = "QUARTERFULL"
        override val onsetDelayForOralInHours = 0.75
    },
    HALF_FULL {
        override val text = "Half full"
        override val serialized = "HALFFULL"
        override val onsetDelayForOralInHours = 1.5
    },
    FULL {
        override val text = "Full"
        override val serialized = "FULL"
        override val onsetDelayForOralInHours: Double = 3.0
    },
    VERY_FULL {
        override val text = "Very full"
        override val serialized = "VERYFULL"
        override val onsetDelayForOralInHours: Double = 4.0
    };

    abstract val text: String
    abstract val serialized: String
    abstract val onsetDelayForOralInHours: Double
}

object StomachFullnessSerializer : KSerializer<StomachFullness> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DayOfWeek", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: StomachFullness) {
        encoder.encodeString(value.serialized)
    }

    override fun deserialize(decoder: Decoder): StomachFullness {
        val value = decoder.decodeString()
        val fullness = StomachFullness.entries.find { it.serialized == value }
        if (fullness == null) {
            throw IllegalArgumentException("$value is not a valid stomach fullness")
        } else {
            return fullness
        }
    }
}