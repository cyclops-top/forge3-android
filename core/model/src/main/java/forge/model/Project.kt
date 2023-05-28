package forge.model

import android.os.Parcelable
import forge.model.serialization.InstantParceler
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.serialization.Serializable

/**
 *
 * @author justin on 2022/12/3
 */

@Parcelize
@TypeParceler<Instant, InstantParceler>
@Serializable
data class Project(
    val id: Long = 0,
    val name: String,
    val description: String,
    val `package`: String,
    val sign: String,
    val icon: String?,
    val private: Boolean,
    val lastVersion: String?,
    val isCollected: Boolean,
    val createdTime: Instant,
) : Parcelable


