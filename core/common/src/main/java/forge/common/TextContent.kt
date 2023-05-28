package forge.common

import android.content.Context
import androidx.annotation.StringRes

/**
 *
 * @author justin on 2022/12/7
 */
sealed interface TextContent {
    fun string(context: Context): String

    companion object {
        val Empty: TextContent = EmptyText
        operator fun invoke(text: String): TextContent = Text(text)
        operator fun invoke(@StringRes stringId: Int, vararg args: Any): TextContent =
            Resource(stringId, args)
    }

    private object EmptyText: TextContent {
        override fun string(context: Context): String {
            return ""
        }
    }

    @JvmInline
    private value class Text(private val value: String) : TextContent {
        override fun string(context: Context): String = value
        override fun toString(): String {
            return value
        }
    }

    private class Resource(@StringRes private val value: Int, val args: Array<out Any>) :
        TextContent {
        override fun string(context: Context): String {
            return if (args.isEmpty()) {
                context.getString(value)
            } else {
                context.getString(value, *args)
            }
        }

        override fun toString(): String {
            return "Android string resource [$value(${args.joinToString()})]"
        }
    }
}