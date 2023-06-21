package com.hwaryun.designsystem.ui.asphalt

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hwaryun.designsystem.R

val MaisonNeue = FontFamily(
    Font(R.font.maison_neue_book),
    Font(R.font.maison_neue_demi, FontWeight.Medium),
    Font(R.font.maison_neue_bold, FontWeight.Bold),
)

class AsphaltTypography internal constructor(
    val titleHero: TextStyle,
    val titleExtraLarge: TextStyle,
    val titleLarge: TextStyle,
    val titleModerateBold: TextStyle,
    val titleModerateDemi: TextStyle,
    val titleSmallBold: TextStyle,
    val titleSmallDemi: TextStyle,
    val titleTinyBold: TextStyle,
    val titleTinyDemi: TextStyle,
    val bodyModerate: TextStyle,
    val bodySmall: TextStyle,
    val captionModerateDemi: TextStyle,
    val captionModerateBook: TextStyle,
    val captionSmallDemi: TextStyle,
    val captionSmallBook: TextStyle,
) {

    constructor(
        defaultFontFamily: FontFamily = MaisonNeue,
        titleHero: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 44.sp,
        ),
        titleExtraLarge: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 36.sp,
        ),
        titleLarge: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            lineHeight = 28.sp,
        ),
        titleModerateBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        titleModerateDemi: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        titleSmallBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
        ),
        titleSmallDemi: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
        ),
        titleTinyBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        titleTinyDemi: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        bodyModerate: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp,
        ),
        bodySmall: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        captionModerateDemi: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            lineHeight = 16.sp,
        ),
        captionModerateBook: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 16.sp,
        ),
        captionSmallDemi: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        ),
        captionSmallBook: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        ),
    ) : this(
        titleHero.withDefaultFontFamily(defaultFontFamily),
        titleExtraLarge.withDefaultFontFamily(defaultFontFamily),
        titleLarge.withDefaultFontFamily(defaultFontFamily),
        titleModerateBold.withDefaultFontFamily(defaultFontFamily),
        titleModerateDemi.withDefaultFontFamily(defaultFontFamily),
        titleSmallBold.withDefaultFontFamily(defaultFontFamily),
        titleSmallDemi.withDefaultFontFamily(defaultFontFamily),
        titleTinyBold.withDefaultFontFamily(defaultFontFamily),
        titleTinyDemi.withDefaultFontFamily(defaultFontFamily),
        bodyModerate.withDefaultFontFamily(defaultFontFamily),
        bodySmall.withDefaultFontFamily(defaultFontFamily),
        captionModerateDemi.withDefaultFontFamily(defaultFontFamily),
        captionModerateBook.withDefaultFontFamily(defaultFontFamily),
        captionSmallDemi.withDefaultFontFamily(defaultFontFamily),
        captionSmallBook.withDefaultFontFamily(defaultFontFamily),
    )

    fun copy(
        titleHero: TextStyle = this.titleHero,
        titleExtraLarge: TextStyle = this.titleExtraLarge,
        titleLarge: TextStyle = this.titleLarge,
        titleModerateBold: TextStyle = this.titleModerateBold,
        titleModerateDemi: TextStyle = this.titleModerateDemi,
        titleSmallBold: TextStyle = this.titleSmallBold,
        titleSmallDemi: TextStyle = this.titleSmallDemi,
        titleTinyBold: TextStyle = this.titleTinyBold,
        titleTinyDemi: TextStyle = this.titleTinyDemi,
        bodyModerate: TextStyle = this.bodyModerate,
        bodySmall: TextStyle = this.bodySmall,
        captionModerateDemi: TextStyle = this.captionModerateDemi,
        captionModerateBook: TextStyle = this.captionModerateBook,
        captionSmallDemi: TextStyle = this.captionSmallDemi,
        captionSmallBook: TextStyle = this.captionSmallBook,
    ): AsphaltTypography = AsphaltTypography(
        titleHero,
        titleExtraLarge,
        titleLarge,
        titleModerateBold,
        titleModerateDemi,
        titleSmallBold,
        titleSmallDemi,
        titleTinyBold,
        titleTinyDemi,
        bodyModerate,
        bodySmall,
        captionModerateDemi,
        captionModerateBook,
        captionSmallDemi,
        captionSmallBook,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AsphaltTypography) return false

        if (titleHero != other.titleHero) return false
        if (titleExtraLarge != other.titleExtraLarge) return false
        if (titleLarge != other.titleLarge) return false
        if (titleModerateBold != other.titleModerateBold) return false
        if (titleModerateDemi != other.titleModerateDemi) return false
        if (titleSmallBold != other.titleSmallBold) return false
        if (titleSmallDemi != other.titleSmallDemi) return false
        if (titleTinyBold != other.titleTinyBold) return false
        if (titleTinyDemi != other.titleTinyDemi) return false
        if (bodyModerate != other.bodyModerate) return false
        if (bodySmall != other.bodySmall) return false
        if (captionModerateDemi != other.captionModerateDemi) return false
        if (captionModerateBook != other.captionModerateBook) return false
        if (captionSmallDemi != other.captionSmallDemi) return false
        if (captionSmallBook != other.captionSmallBook) return false

        return true
    }

    override fun hashCode(): Int {
        var result = titleHero.hashCode()
        result = 31 * result + titleExtraLarge.hashCode()
        result = 31 * result + titleLarge.hashCode()
        result = 31 * result + titleModerateBold.hashCode()
        result = 31 * result + titleModerateDemi.hashCode()
        result = 31 * result + titleSmallBold.hashCode()
        result = 31 * result + titleSmallDemi.hashCode()
        result = 31 * result + titleTinyBold.hashCode()
        result = 31 * result + titleTinyDemi.hashCode()
        result = 31 * result + bodyModerate.hashCode()
        result = 31 * result + bodySmall.hashCode()
        result = 31 * result + captionModerateDemi.hashCode()
        result = 31 * result + captionModerateBook.hashCode()
        return result
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography = staticCompositionLocalOf { AsphaltTypography() }