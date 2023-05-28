package forge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import forge.ui.R

private val _yuppySc = FontFamily(
    Font(R.font.yuppy_sc)
)
val FontFamily.Companion.YuppySc get() = _yuppySc


// Set of Material typography styles to start with
val Typography = Typography()
    .let { t->
        t.copy(
            displayLarge = t.displayLarge.copy(fontFamily = FontFamily.YuppySc),
            displayMedium =t.displayMedium.copy(fontFamily = FontFamily.YuppySc),
            displaySmall =t.displaySmall.copy(fontFamily = FontFamily.YuppySc),
            headlineLarge =t.headlineLarge.copy(fontFamily = FontFamily.YuppySc),
            headlineMedium =t.headlineMedium.copy(fontFamily = FontFamily.YuppySc),
            headlineSmall =t.headlineSmall.copy(fontFamily = FontFamily.YuppySc),
            titleLarge =t.titleLarge.copy(fontFamily = FontFamily.YuppySc),
            titleMedium =t.titleMedium.copy(fontFamily = FontFamily.YuppySc),
            titleSmall =t.titleSmall.copy(fontFamily = FontFamily.YuppySc),
            bodyLarge =t.bodyLarge.copy(fontFamily = FontFamily.YuppySc),
            bodyMedium =t.bodyMedium.copy(fontFamily = FontFamily.YuppySc),
            bodySmall =t.bodySmall.copy(fontFamily = FontFamily.YuppySc),
            labelLarge =t.labelLarge.copy(fontFamily = FontFamily.YuppySc),
            labelMedium =t.labelMedium.copy(fontFamily = FontFamily.YuppySc),
            labelSmall = t.labelSmall.copy(fontFamily = FontFamily.YuppySc)
        )
    }