package forge.model

import androidx.annotation.Keep
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

@Keep
@Serializable
data class RemoteFile(
    @SerialName("url")
    val url: String,
    @SerialName("size")
    val size: Long,
    @SerialName("createdTime")
    val createdTime: Instant,
) {
    val displaySize: String by lazy {
        if (size <= 0L) return@lazy "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        format.format(
            size / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }
    companion object{
        private val format = DecimalFormat("#,##0.#")
    }
}