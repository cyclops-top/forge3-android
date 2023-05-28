package forge.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import forge.common.TextContent

data class NavigationItem(
    val icon: IconContent,
    val selectedIcon: IconContent = icon,
    val label: TextContent,
    val selectedLabel: TextContent = label
)

@Composable
fun RowScope.NavigationBarItem(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            when (val icon = if (selected) item.icon else item.selectedIcon) {
                is IconContent.DrawableResourceIcon -> Image(
                    painter = painterResource(id = icon.id),
                    contentDescription = ""
                )

                is IconContent.ImageVectorIcon -> Image(
                    imageVector = icon.imageVector,
                    contentDescription = ""
                )
            }
        },
        modifier = modifier,
        enabled = enabled,
        label = {
            Text((if (selected) item.label else item.selectedLabel).string())
        },
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource
    )
}