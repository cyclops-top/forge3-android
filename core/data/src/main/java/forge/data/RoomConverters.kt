@file:Suppress("unused")

package forge.data

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import java.math.BigDecimal
import java.util.Date

object RoomConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun formBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.toBigDecimal()
    }

    @TypeConverter
    fun formStringArray(value: Array<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toStringArray(value: String?): Array<String>? {
        value ?: return null
        if (value.isEmpty()) return emptyArray()
        return value.split(",").toTypedArray()
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        value ?: return null
        if (value.isEmpty()) return emptyList()
        return value.split(",")
    }

    @TypeConverter
    fun formStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun instance(value: Instant?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun instance(value: String?): Instant? {
        return value?.let { Instant.parse(it) }
    }


}