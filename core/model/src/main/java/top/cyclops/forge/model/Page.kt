package top.cyclops.forge.model

import kotlinx.serialization.Serializable


@Serializable
data class Page<T>(
    val content: List<T>,
    val number: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {

    fun <U> map(converter: (T) -> U): Page<U> {
        val contentU = content.map(converter)
        return Page(
            contentU,
            number,
            size,
            totalElements,
            totalPages,
            isFirst,
            isLast,
            hasNext,
            hasPrevious
        )
    }
}
