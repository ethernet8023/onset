package computer.ethernet.onset.ui.tabs.settings

import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ShulginRatingOptionSerializer : KSerializer<ShulginRatingOption> {
    override val descriptor = PrimitiveSerialDescriptor("ShulginRatingOption", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ShulginRatingOption) {
        encoder.encodeString(value.rawValue)
    }

    override fun deserialize(decoder: Decoder): ShulginRatingOption {
        val ratingRawValue = decoder.decodeString()
        val foundRating = ShulginRatingOption.entries.firstOrNull {
            it.rawValue == ratingRawValue
        }
        return foundRating ?: ShulginRatingOption.FOUR_PLUS
    }
}