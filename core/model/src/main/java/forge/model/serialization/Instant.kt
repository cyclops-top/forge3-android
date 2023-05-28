package forge.model.serialization

import android.os.Parcel
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parceler

object InstantParceler : Parceler<Instant> {
    override fun create(parcel: Parcel): Instant {

        return Instant.fromEpochMilliseconds(parcel.readLong())
    }

    override fun Instant.write(parcel: Parcel, flags: Int) {
        parcel.writeLong(toEpochMilliseconds())
    }
}