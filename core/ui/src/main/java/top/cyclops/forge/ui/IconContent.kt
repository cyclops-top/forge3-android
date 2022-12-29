package top.cyclops.forge.ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 *
 * @author justin on 2022/12/7
 */
sealed interface IconContent {

    companion object {
        operator fun invoke(imageVector: ImageVector): IconContent = ImageVectorIcon(imageVector)
        operator fun invoke(@DrawableRes id: Int): IconContent = DrawableResourceIcon(id)
    }

    @JvmInline
    value class ImageVectorIcon(val imageVector: ImageVector) : IconContent

    @JvmInline
    value class DrawableResourceIcon(@DrawableRes val id: Int) : IconContent
}
