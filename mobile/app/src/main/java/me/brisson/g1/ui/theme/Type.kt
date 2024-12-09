package me.brisson.g1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.brisson.g1.R

val interFontFamily = FontFamily(
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
)

val newsreaderFontFamily = FontFamily(
    Font(R.font.newsreader_extra_light, FontWeight.ExtraLight),
    Font(R.font.newsreader_light, FontWeight.Light),
    Font(R.font.newsreader_regular, FontWeight.Normal),
    Font(R.font.newsreader_medium, FontWeight.Medium),
    Font(R.font.newsreader_semibold, FontWeight.SemiBold),
    Font(R.font.newsreader_bold, FontWeight.Bold),
    Font(R.font.newsreader_extra_bold, FontWeight.ExtraBold),
)

private val defaultTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = newsreaderFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = newsreaderFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = newsreaderFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = newsreaderFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = newsreaderFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = newsreaderFontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = newsreaderFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = newsreaderFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = newsreaderFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = newsreaderFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = newsreaderFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = newsreaderFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = newsreaderFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = newsreaderFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = newsreaderFontFamily)
)
