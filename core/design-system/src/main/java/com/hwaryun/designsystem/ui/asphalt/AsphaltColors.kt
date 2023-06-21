package com.hwaryun.designsystem.ui.asphalt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import com.hwaryun.designsystem.ui.Black500

@Stable
class AsphaltColors(
    gojek_green_50: Color,
    gojek_green_100: Color,
    gojek_green_200: Color,
    gojek_green_300: Color,
    gojek_green_400: Color,
    gojek_green_500: Color,
    gojek_green_600: Color,
    gojek_green_700: Color,
    gojek_green_800: Color,
    gojek_green_900: Color,
    payment_blue_50: Color,
    payment_blue_100: Color,
    payment_blue_200: Color,
    payment_blue_300: Color,
    payment_blue_400: Color,
    payment_blue_500: Color,
    payment_blue_600: Color,
    payment_blue_700: Color,
    payment_blue_800: Color,
    payment_blue_900: Color,
    partner_purple_50: Color,
    partner_purple_100: Color,
    partner_purple_200: Color,
    partner_purple_300: Color,
    partner_purple_400: Color,
    partner_purple_500: Color,
    partner_purple_600: Color,
    partner_purple_700: Color,
    partner_purple_800: Color,
    partner_purple_900: Color,
    retail_red_50: Color,
    retail_red_100: Color,
    retail_red_200: Color,
    retail_red_300: Color,
    retail_red_400: Color,
    retail_red_500: Color,
    retail_red_600: Color,
    retail_red_700: Color,
    retail_red_800: Color,
    retail_red_900: Color,
    odd_job_orange_50: Color,
    odd_job_orange_100: Color,
    odd_job_orange_200: Color,
    odd_job_orange_300: Color,
    odd_job_orange_400: Color,
    odd_job_orange_500: Color,
    odd_job_orange_600: Color,
    odd_job_orange_700: Color,
    odd_job_orange_800: Color,
    odd_job_orange_900: Color,
    popcorn_pink_50: Color,
    popcorn_pink_100: Color,
    popcorn_pink_200: Color,
    popcorn_pink_300: Color,
    popcorn_pink_400: Color,
    popcorn_pink_500: Color,
    popcorn_pink_600: Color,
    popcorn_pink_700: Color,
    popcorn_pink_800: Color,
    popcorn_pink_900: Color,
    black_50: Color,
    black_100: Color,
    black_200: Color,
    black_300: Color,
    black_400: Color,
    black_500: Color,
    black_600: Color,
    black_700: Color,
    black_800: Color,
    black_900: Color,
    sub_black_50: Color,
    sub_black_100: Color,
    sub_black_200: Color,
    sub_black_300: Color,
    sub_black_400: Color,
    sub_black_500: Color,
    sub_black_600: Color,
    sub_black_700: Color,
    sub_black_800: Color,
    sub_black_900: Color,
    cool_gray_50: Color,
    cool_gray_100: Color,
    cool_gray_200: Color,
    cool_gray_300: Color,
    cool_gray_400: Color,
    cool_gray_500: Color,
    cool_gray_600: Color,
    cool_gray_700: Color,
    cool_gray_800: Color,
    cool_gray_900: Color,
    cool_gray_1cCp_50: Color,
    cool_gray_1cCp_100: Color,
    cool_gray_1cCp_200: Color,
    cool_gray_1cCp_300: Color,
    cool_gray_1cCp_400: Color,
    cool_gray_1cCp_500: Color,
    cool_gray_1cCp_600: Color,
    cool_gray_1cCp_700: Color,
    cool_gray_1cCp_800: Color,
    cool_gray_1cCp_900: Color,
    pure_white_50: Color,
    pure_white_100: Color,
    pure_white_200: Color,
    pure_white_300: Color,
    pure_white_400: Color,
    pure_white_500: Color,
    pure_white_600: Color,
    pure_white_700: Color,
    pure_white_800: Color,
    pure_white_900: Color,
    isLight: Boolean,
) {
    var gojek_green_50 by mutableStateOf(gojek_green_50, structuralEqualityPolicy())
        internal set
    var gojek_green_100 by mutableStateOf(gojek_green_100, structuralEqualityPolicy())
        internal set
    var gojek_green_200 by mutableStateOf(gojek_green_200, structuralEqualityPolicy())
        internal set
    var gojek_green_300 by mutableStateOf(gojek_green_300, structuralEqualityPolicy())
        internal set
    var gojek_green_400 by mutableStateOf(gojek_green_400, structuralEqualityPolicy())
        internal set
    var gojek_green_500 by mutableStateOf(gojek_green_500, structuralEqualityPolicy())
        internal set
    var gojek_green_600 by mutableStateOf(gojek_green_600, structuralEqualityPolicy())
        internal set
    var gojek_green_700 by mutableStateOf(gojek_green_700, structuralEqualityPolicy())
        internal set
    var gojek_green_800 by mutableStateOf(gojek_green_800, structuralEqualityPolicy())
        internal set
    var gojek_green_900 by mutableStateOf(gojek_green_900, structuralEqualityPolicy())
        internal set

    var payment_blue_50 by mutableStateOf(payment_blue_50, structuralEqualityPolicy())
        internal set
    var payment_blue_100 by mutableStateOf(payment_blue_100, structuralEqualityPolicy())
        internal set
    var payment_blue_200 by mutableStateOf(payment_blue_200, structuralEqualityPolicy())
        internal set
    var payment_blue_300 by mutableStateOf(payment_blue_300, structuralEqualityPolicy())
        internal set
    var payment_blue_400 by mutableStateOf(payment_blue_400, structuralEqualityPolicy())
        internal set
    var payment_blue_500 by mutableStateOf(payment_blue_500, structuralEqualityPolicy())
        internal set
    var payment_blue_600 by mutableStateOf(payment_blue_600, structuralEqualityPolicy())
        internal set
    var payment_blue_700 by mutableStateOf(payment_blue_700, structuralEqualityPolicy())
        internal set
    var payment_blue_800 by mutableStateOf(payment_blue_800, structuralEqualityPolicy())
        internal set
    var payment_blue_900 by mutableStateOf(payment_blue_900, structuralEqualityPolicy())
        internal set

    var partner_purple_50 by mutableStateOf(partner_purple_50, structuralEqualityPolicy())
        internal set
    var partner_purple_100 by mutableStateOf(partner_purple_100, structuralEqualityPolicy())
        internal set
    var partner_purple_200 by mutableStateOf(partner_purple_200, structuralEqualityPolicy())
        internal set
    var partner_purple_300 by mutableStateOf(partner_purple_300, structuralEqualityPolicy())
        internal set
    var partner_purple_400 by mutableStateOf(partner_purple_400, structuralEqualityPolicy())
        internal set
    var partner_purple_500 by mutableStateOf(partner_purple_500, structuralEqualityPolicy())
        internal set
    var partner_purple_600 by mutableStateOf(partner_purple_600, structuralEqualityPolicy())
        internal set
    var partner_purple_700 by mutableStateOf(partner_purple_700, structuralEqualityPolicy())
        internal set
    var partner_purple_800 by mutableStateOf(partner_purple_800, structuralEqualityPolicy())
        internal set
    var partner_purple_900 by mutableStateOf(partner_purple_900, structuralEqualityPolicy())
        internal set

    var retail_red_50 by mutableStateOf(retail_red_50, structuralEqualityPolicy())
        internal set
    var retail_red_100 by mutableStateOf(retail_red_100, structuralEqualityPolicy())
        internal set
    var retail_red_200 by mutableStateOf(retail_red_200, structuralEqualityPolicy())
        internal set
    var retail_red_300 by mutableStateOf(retail_red_300, structuralEqualityPolicy())
        internal set
    var retail_red_400 by mutableStateOf(retail_red_400, structuralEqualityPolicy())
        internal set
    var retail_red_500 by mutableStateOf(retail_red_500, structuralEqualityPolicy())
        internal set
    var retail_red_600 by mutableStateOf(retail_red_600, structuralEqualityPolicy())
        internal set
    var retail_red_700 by mutableStateOf(retail_red_700, structuralEqualityPolicy())
        internal set
    var retail_red_800 by mutableStateOf(retail_red_800, structuralEqualityPolicy())
        internal set
    var retail_red_900 by mutableStateOf(retail_red_900, structuralEqualityPolicy())
        internal set

    var odd_job_orange_50 by mutableStateOf(odd_job_orange_50, structuralEqualityPolicy())
        internal set
    var odd_job_orange_100 by mutableStateOf(odd_job_orange_100, structuralEqualityPolicy())
        internal set
    var odd_job_orange_200 by mutableStateOf(odd_job_orange_200, structuralEqualityPolicy())
        internal set
    var odd_job_orange_300 by mutableStateOf(odd_job_orange_300, structuralEqualityPolicy())
        internal set
    var odd_job_orange_400 by mutableStateOf(odd_job_orange_400, structuralEqualityPolicy())
        internal set

    var odd_job_orange_500 by mutableStateOf(odd_job_orange_500, structuralEqualityPolicy())
        internal set
    var odd_job_orange_600 by mutableStateOf(odd_job_orange_600, structuralEqualityPolicy())
        internal set
    var odd_job_orange_700 by mutableStateOf(odd_job_orange_700, structuralEqualityPolicy())
        internal set
    var odd_job_orange_800 by mutableStateOf(odd_job_orange_800, structuralEqualityPolicy())
        internal set
    var odd_job_orange_900 by mutableStateOf(odd_job_orange_900, structuralEqualityPolicy())
        internal set

    var popcorn_pink_50 by mutableStateOf(popcorn_pink_50, structuralEqualityPolicy())
        internal set
    var popcorn_pink_100 by mutableStateOf(popcorn_pink_100, structuralEqualityPolicy())
        internal set
    var popcorn_pink_200 by mutableStateOf(popcorn_pink_200, structuralEqualityPolicy())
        internal set
    var popcorn_pink_300 by mutableStateOf(popcorn_pink_300, structuralEqualityPolicy())
        internal set
    var popcorn_pink_400 by mutableStateOf(popcorn_pink_400, structuralEqualityPolicy())
        internal set

    var popcorn_pink_500 by mutableStateOf(popcorn_pink_500, structuralEqualityPolicy())
        internal set
    var popcorn_pink_600 by mutableStateOf(popcorn_pink_600, structuralEqualityPolicy())
        internal set
    var popcorn_pink_700 by mutableStateOf(popcorn_pink_700, structuralEqualityPolicy())
        internal set
    var popcorn_pink_800 by mutableStateOf(popcorn_pink_800, structuralEqualityPolicy())
        internal set
    var popcorn_pink_900 by mutableStateOf(popcorn_pink_900, structuralEqualityPolicy())
        internal set

    var black_50 by mutableStateOf(black_50, structuralEqualityPolicy())
        internal set
    var black_100 by mutableStateOf(black_100, structuralEqualityPolicy())
        internal set
    var black_200 by mutableStateOf(black_200, structuralEqualityPolicy())
        internal set
    var black_300 by mutableStateOf(black_300, structuralEqualityPolicy())
        internal set
    var black_400 by mutableStateOf(black_400, structuralEqualityPolicy())
        internal set
    var black_500 by mutableStateOf(black_500, structuralEqualityPolicy())
        internal set
    var black_600 by mutableStateOf(black_600, structuralEqualityPolicy())
        internal set
    var black_700 by mutableStateOf(black_700, structuralEqualityPolicy())
        internal set
    var black_800 by mutableStateOf(black_800, structuralEqualityPolicy())
        internal set
    var black_900 by mutableStateOf(black_900, structuralEqualityPolicy())
        internal set

    var sub_black_50 by mutableStateOf(sub_black_50, structuralEqualityPolicy())
        internal set
    var sub_black_100 by mutableStateOf(sub_black_100, structuralEqualityPolicy())
        internal set
    var sub_black_200 by mutableStateOf(sub_black_200, structuralEqualityPolicy())
        internal set
    var sub_black_300 by mutableStateOf(sub_black_300, structuralEqualityPolicy())
        internal set
    var sub_black_400 by mutableStateOf(sub_black_400, structuralEqualityPolicy())
        internal set

    var sub_black_500 by mutableStateOf(sub_black_500, structuralEqualityPolicy())
        internal set
    var sub_black_600 by mutableStateOf(sub_black_600, structuralEqualityPolicy())
        internal set
    var sub_black_700 by mutableStateOf(sub_black_700, structuralEqualityPolicy())
        internal set
    var sub_black_800 by mutableStateOf(sub_black_800, structuralEqualityPolicy())
        internal set
    var sub_black_900 by mutableStateOf(sub_black_900, structuralEqualityPolicy())
        internal set

    var cool_gray_50 by mutableStateOf(cool_gray_50, structuralEqualityPolicy())
        internal set
    var cool_gray_100 by mutableStateOf(cool_gray_100, structuralEqualityPolicy())
        internal set
    var cool_gray_200 by mutableStateOf(cool_gray_200, structuralEqualityPolicy())
        internal set
    var cool_gray_300 by mutableStateOf(cool_gray_300, structuralEqualityPolicy())
        internal set
    var cool_gray_400 by mutableStateOf(cool_gray_400, structuralEqualityPolicy())
        internal set
    var cool_gray_500 by mutableStateOf(cool_gray_500, structuralEqualityPolicy())
        internal set
    var cool_gray_600 by mutableStateOf(cool_gray_600, structuralEqualityPolicy())
        internal set
    var cool_gray_700 by mutableStateOf(cool_gray_700, structuralEqualityPolicy())
        internal set
    var cool_gray_800 by mutableStateOf(cool_gray_800, structuralEqualityPolicy())
        internal set
    var cool_gray_900 by mutableStateOf(cool_gray_900, structuralEqualityPolicy())
        internal set

    var cool_gray_1cCp_50 by mutableStateOf(cool_gray_1cCp_50, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_100 by mutableStateOf(cool_gray_1cCp_100, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_200 by mutableStateOf(cool_gray_1cCp_200, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_300 by mutableStateOf(cool_gray_1cCp_300, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_400 by mutableStateOf(cool_gray_1cCp_400, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_500 by mutableStateOf(cool_gray_1cCp_500, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_600 by mutableStateOf(cool_gray_1cCp_600, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_700 by mutableStateOf(cool_gray_1cCp_700, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_800 by mutableStateOf(cool_gray_1cCp_800, structuralEqualityPolicy())
        internal set
    var cool_gray_1cCp_900 by mutableStateOf(cool_gray_1cCp_900, structuralEqualityPolicy())
        internal set

    var pure_white_50 by mutableStateOf(pure_white_50, structuralEqualityPolicy())
        internal set
    var pure_white_100 by mutableStateOf(pure_white_100, structuralEqualityPolicy())
        internal set
    var pure_white_200 by mutableStateOf(pure_white_200, structuralEqualityPolicy())
        internal set
    var pure_white_300 by mutableStateOf(pure_white_300, structuralEqualityPolicy())
        internal set
    var pure_white_400 by mutableStateOf(pure_white_400, structuralEqualityPolicy())
        internal set
    var pure_white_500 by mutableStateOf(pure_white_500, structuralEqualityPolicy())
        internal set
    var pure_white_600 by mutableStateOf(pure_white_600, structuralEqualityPolicy())
        internal set
    var pure_white_700 by mutableStateOf(pure_white_700, structuralEqualityPolicy())
        internal set
    var pure_white_800 by mutableStateOf(pure_white_800, structuralEqualityPolicy())
        internal set
    var pure_white_900 by mutableStateOf(pure_white_900, structuralEqualityPolicy())
        internal set

    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    fun copy(
        gojek_green_50: Color = this.gojek_green_50,
        gojek_green_100: Color = this.gojek_green_100,
        gojek_green_200: Color = this.gojek_green_200,
        gojek_green_300: Color = this.gojek_green_300,
        gojek_green_400: Color = this.gojek_green_400,
        gojek_green_500: Color = this.gojek_green_500,
        gojek_green_600: Color = this.gojek_green_600,
        gojek_green_700: Color = this.gojek_green_700,
        gojek_green_800: Color = this.gojek_green_800,
        gojek_green_900: Color = this.gojek_green_900,
        payment_blue_50: Color = this.payment_blue_50,
        payment_blue_100: Color = this.payment_blue_100,
        payment_blue_200: Color = this.payment_blue_200,
        payment_blue_300: Color = this.payment_blue_300,
        payment_blue_400: Color = this.payment_blue_400,
        payment_blue_500: Color = this.payment_blue_500,
        payment_blue_600: Color = this.payment_blue_600,
        payment_blue_700: Color = this.payment_blue_700,
        payment_blue_800: Color = this.payment_blue_800,
        payment_blue_900: Color = this.payment_blue_900,
        partner_purple_50: Color = this.partner_purple_50,
        partner_purple_100: Color = this.partner_purple_100,
        partner_purple_200: Color = this.partner_purple_200,
        partner_purple_300: Color = this.partner_purple_300,
        partner_purple_400: Color = this.partner_purple_400,
        partner_purple_500: Color = this.partner_purple_500,
        partner_purple_600: Color = this.partner_purple_600,
        partner_purple_700: Color = this.partner_purple_700,
        partner_purple_800: Color = this.partner_purple_800,
        partner_purple_900: Color = this.partner_purple_900,
        retail_red_50: Color = this.retail_red_50,
        retail_red_100: Color = this.retail_red_100,
        retail_red_200: Color = this.retail_red_200,
        retail_red_300: Color = this.retail_red_300,
        retail_red_400: Color = this.retail_red_400,
        retail_red_500: Color = this.retail_red_500,
        retail_red_600: Color = this.retail_red_600,
        retail_red_700: Color = this.retail_red_700,
        retail_red_800: Color = this.retail_red_800,
        retail_red_900: Color = this.retail_red_900,
        odd_job_orange_50: Color = this.odd_job_orange_50,
        odd_job_orange_100: Color = this.odd_job_orange_100,
        odd_job_orange_200: Color = this.odd_job_orange_200,
        odd_job_orange_300: Color = this.odd_job_orange_300,
        odd_job_orange_400: Color = this.odd_job_orange_400,
        odd_job_orange_500: Color = this.odd_job_orange_500,
        odd_job_orange_600: Color = this.odd_job_orange_600,
        odd_job_orange_700: Color = this.odd_job_orange_700,
        odd_job_orange_800: Color = this.odd_job_orange_800,
        odd_job_orange_900: Color = this.odd_job_orange_900,
        popcorn_pink_50: Color = this.popcorn_pink_50,
        popcorn_pink_100: Color = this.popcorn_pink_100,
        popcorn_pink_200: Color = this.popcorn_pink_200,
        popcorn_pink_300: Color = this.popcorn_pink_300,
        popcorn_pink_400: Color = this.popcorn_pink_400,
        popcorn_pink_500: Color = this.popcorn_pink_500,
        popcorn_pink_600: Color = this.popcorn_pink_600,
        popcorn_pink_700: Color = this.popcorn_pink_700,
        popcorn_pink_800: Color = this.popcorn_pink_800,
        popcorn_pink_900: Color = this.popcorn_pink_900,
        black_50: Color = this.black_50,
        black_100: Color = this.black_100,
        black_200: Color = this.black_200,
        black_300: Color = this.black_300,
        black_400: Color = this.black_400,
        black_500: Color = this.black_500,
        black_600: Color = this.black_600,
        black_700: Color = this.black_700,
        black_800: Color = this.black_800,
        black_900: Color = this.black_900,
        sub_black_50: Color = this.sub_black_50,
        sub_black_100: Color = this.sub_black_100,
        sub_black_200: Color = this.sub_black_200,
        sub_black_300: Color = this.sub_black_300,
        sub_black_400: Color = this.sub_black_400,
        sub_black_500: Color = this.sub_black_500,
        sub_black_600: Color = this.sub_black_600,
        sub_black_700: Color = this.sub_black_700,
        sub_black_800: Color = this.sub_black_800,
        sub_black_900: Color = this.sub_black_900,
        cool_gray_50: Color = this.cool_gray_50,
        cool_gray_100: Color = this.cool_gray_100,
        cool_gray_200: Color = this.cool_gray_200,
        cool_gray_300: Color = this.cool_gray_300,
        cool_gray_400: Color = this.cool_gray_400,
        cool_gray_500: Color = this.cool_gray_500,
        cool_gray_600: Color = this.cool_gray_600,
        cool_gray_700: Color = this.cool_gray_700,
        cool_gray_800: Color = this.cool_gray_800,
        cool_gray_900: Color = this.cool_gray_900,
        cool_gray_1cCp_50: Color = this.cool_gray_1cCp_50,
        cool_gray_1cCp_100: Color = this.cool_gray_1cCp_100,
        cool_gray_1cCp_200: Color = this.cool_gray_1cCp_200,
        cool_gray_1cCp_300: Color = this.cool_gray_1cCp_300,
        cool_gray_1cCp_400: Color = this.cool_gray_1cCp_400,
        cool_gray_1cCp_500: Color = this.cool_gray_1cCp_500,
        cool_gray_1cCp_600: Color = this.cool_gray_1cCp_600,
        cool_gray_1cCp_700: Color = this.cool_gray_1cCp_700,
        cool_gray_1cCp_800: Color = this.cool_gray_1cCp_800,
        cool_gray_1cCp_900: Color = this.cool_gray_1cCp_900,
        pure_white_50: Color = this.pure_white_50,
        pure_white_100: Color = this.pure_white_100,
        pure_white_200: Color = this.pure_white_200,
        pure_white_300: Color = this.pure_white_300,
        pure_white_400: Color = this.pure_white_400,
        pure_white_500: Color = this.pure_white_500,
        pure_white_600: Color = this.pure_white_600,
        pure_white_700: Color = this.pure_white_700,
        pure_white_800: Color = this.pure_white_800,
        pure_white_900: Color = this.pure_white_900,
        isLight: Boolean = this.isLight,
    ): AsphaltColors = AsphaltColors(
        gojek_green_50,
        gojek_green_100,
        gojek_green_200,
        gojek_green_300,
        gojek_green_400,
        gojek_green_500,
        gojek_green_600,
        gojek_green_700,
        gojek_green_800,
        gojek_green_900,
        payment_blue_50,
        payment_blue_100,
        payment_blue_200,
        payment_blue_300,
        payment_blue_400,
        payment_blue_500,
        payment_blue_600,
        payment_blue_700,
        payment_blue_800,
        payment_blue_900,
        partner_purple_50,
        partner_purple_100,
        partner_purple_200,
        partner_purple_300,
        partner_purple_400,
        partner_purple_500,
        partner_purple_600,
        partner_purple_700,
        partner_purple_800,
        partner_purple_900,
        retail_red_50,
        retail_red_100,
        retail_red_200,
        retail_red_300,
        retail_red_400,
        retail_red_500,
        retail_red_600,
        retail_red_700,
        retail_red_800,
        retail_red_900,
        odd_job_orange_50,
        odd_job_orange_100,
        odd_job_orange_200,
        odd_job_orange_300,
        odd_job_orange_400,
        odd_job_orange_500,
        odd_job_orange_600,
        odd_job_orange_700,
        odd_job_orange_800,
        odd_job_orange_900,
        popcorn_pink_50,
        popcorn_pink_100,
        popcorn_pink_200,
        popcorn_pink_300,
        popcorn_pink_400,
        popcorn_pink_500,
        popcorn_pink_600,
        popcorn_pink_700,
        popcorn_pink_800,
        popcorn_pink_900,
        black_50,
        black_100,
        black_200,
        black_300,
        black_400,
        black_500,
        black_600,
        black_700,
        black_800,
        black_900,
        sub_black_50,
        sub_black_100,
        sub_black_200,
        sub_black_300,
        sub_black_400,
        sub_black_500,
        sub_black_600,
        sub_black_700,
        sub_black_800,
        sub_black_900,
        cool_gray_50,
        cool_gray_100,
        cool_gray_200,
        cool_gray_300,
        cool_gray_400,
        cool_gray_500,
        cool_gray_600,
        cool_gray_700,
        cool_gray_800,
        cool_gray_900,
        cool_gray_1cCp_50,
        cool_gray_1cCp_100,
        cool_gray_1cCp_200,
        cool_gray_1cCp_300,
        cool_gray_1cCp_400,
        cool_gray_1cCp_500,
        cool_gray_1cCp_600,
        cool_gray_1cCp_700,
        cool_gray_1cCp_800,
        cool_gray_1cCp_900,
        pure_white_50,
        pure_white_100,
        pure_white_200,
        pure_white_300,
        pure_white_400,
        pure_white_500,
        pure_white_600,
        pure_white_700,
        pure_white_800,
        pure_white_900,
        isLight
    )
}


fun lightAsphaltColors(
    gojek_green_50: Color = Color(0xFFE6F7E7),
    gojek_green_100: Color = Color(0xFFB0E5B6),
    gojek_green_200: Color = Color(0xFF8AD892),
    gojek_green_300: Color = Color(0xFF54C661),
    gojek_green_400: Color = Color(0xFF33BB42),
    gojek_green_500: Color = Color(0xFF00AA13),
    gojek_green_600: Color = Color(0xFF009B11),
    gojek_green_700: Color = Color(0xFF00790D),
    gojek_green_800: Color = Color(0xFF005E0A),
    gojek_green_900: Color = Color(0xFF004708),
    payment_blue_50: Color = Color(0xFFE6F7FB),
    payment_blue_100: Color = Color(0xFFB0E6F2),
    payment_blue_200: Color = Color(0xFF8ADAEC),
    payment_blue_300: Color = Color(0xFF54C9E4),
    payment_blue_400: Color = Color(0xFF33BEDE),
    payment_blue_500: Color = Color(0xFF00AED6),
    payment_blue_600: Color = Color(0xFF009EC3),
    payment_blue_700: Color = Color(0xFF007C98),
    payment_blue_800: Color = Color(0xFF006076),
    payment_blue_900: Color = Color(0xFF00495A),
    partner_purple_50: Color = Color(0xFFF5EBF4),
    partner_purple_100: Color = Color(0xFFDFBFDC),
    partner_purple_200: Color = Color(0xFFD0A1CB),
    partner_purple_300: Color = Color(0xFFBB76B3),
    partner_purple_400: Color = Color(0xFFAD5BA5),
    partner_purple_500: Color = Color(0xFF99328E),
    partner_purple_600: Color = Color(0xFF8B2E81),
    partner_purple_700: Color = Color(0xFF6D2465),
    partner_purple_800: Color = Color(0xFF541C4E),
    partner_purple_900: Color = Color(0xFF40153C),
    retail_red_50: Color = Color(0xFFFDE9EB),
    retail_red_100: Color = Color(0xFFFABCC1),
    retail_red_200: Color = Color(0xFFF79CA3),
    retail_red_300: Color = Color(0xFFF46E79),
    retail_red_400: Color = Color(0xFFF1525F),
    retail_red_500: Color = Color(0xFFEE2737),
    retail_red_600: Color = Color(0xFFD92332),
    retail_red_700: Color = Color(0xFFA91C27),
    retail_red_800: Color = Color(0xFF83151E),
    retail_red_900: Color = Color(0xFF641017),
    odd_job_orange_50: Color = Color(0xFFFEF0E6),
    odd_job_orange_100: Color = Color(0xFFFACFB0),
    odd_job_orange_200: Color = Color(0xFFF8B88A),
    odd_job_orange_300: Color = Color(0xFFF59754),
    odd_job_orange_400: Color = Color(0xFFF38333),
    odd_job_orange_500: Color = Color(0xFFF06400),
    odd_job_orange_600: Color = Color(0xFFDA5B00),
    odd_job_orange_700: Color = Color(0xFFAA4700),
    odd_job_orange_800: Color = Color(0xFF843700),
    odd_job_orange_900: Color = Color(0xFF652A00),
    popcorn_pink_50: Color = Color(0xFFFCE8F4),
    popcorn_pink_100: Color = Color(0xFFF5B8DE),
    popcorn_pink_200: Color = Color(0xFFF095CE),
    popcorn_pink_300: Color = Color(0xFFEA65B8),
    popcorn_pink_400: Color = Color(0xFFE547AA),
    popcorn_pink_500: Color = Color(0xFFDF1995),
    popcorn_pink_600: Color = Color(0xFFCB1788),
    popcorn_pink_700: Color = Color(0xFF9E126A),
    popcorn_pink_800: Color = Color(0xFF7B0E52),
    popcorn_pink_900: Color = Color(0xFF5E0B3F),
    black_50: Color = Color(0xFFE7E8E9),
    black_100: Color = Color(0xFFB5B7BA),
    black_200: Color = Color(0xFF919598),
    black_300: Color = Color(0xFF5F646A),
    black_400: Color = Color(0xFF40464D),
    black_500: Color = Color(0xFF101820),
    black_600: Color = Color(0xFF0F161D),
    black_700: Color = Color(0xFF0B1117),
    black_800: Color = Color(0xFF090D12),
    black_900: Color = Color(0xFF070A0D),
    sub_black_50: Color = Color(0xFFEDEDED),
    sub_black_100: Color = Color(0xFFC7C7C7),
    sub_black_200: Color = Color(0xFFABACAC),
    sub_black_300: Color = Color(0xFF858686),
    sub_black_400: Color = Color(0xFF6D6E6E),
    sub_black_500: Color = Color(0xFF494A4A),
    sub_black_600: Color = Color(0xFF424343),
    sub_black_700: Color = Color(0xFF343535),
    sub_black_800: Color = Color(0xFF282929),
    sub_black_900: Color = Color(0xFF1F1F1F),
    cool_gray_50: Color = Color(0xFFF1F2F2),
    cool_gray_100: Color = Color(0xFFD4D5D6),
    cool_gray_200: Color = Color(0xFFC0C1C2),
    cool_gray_300: Color = Color(0xFFA3A5A7),
    cool_gray_400: Color = Color(0xFF919395),
    cool_gray_500: Color = Color(0xFF75787B),
    cool_gray_600: Color = Color(0xFF6A6D70),
    cool_gray_700: Color = Color(0xFF535557),
    cool_gray_800: Color = Color(0xFF404244),
    cool_gray_900: Color = Color(0xFF313234),
    cool_gray_1cCp_50: Color = Color(0xFFFBFBFB),
    cool_gray_1cCp_100: Color = Color(0xFFF3F3F2),
    cool_gray_1cCp_200: Color = Color(0xFFEEEEEC),
    cool_gray_1cCp_300: Color = Color(0xFFE6E6E4),
    cool_gray_1cCp_400: Color = Color(0xFFE1E1DE),
    cool_gray_1cCp_500: Color = Color(0xFFD9D9D6),
    cool_gray_1cCp_600: Color = Color(0xFFC5C5C3),
    cool_gray_1cCp_700: Color = Color(0xFF9A9A98),
    cool_gray_1cCp_800: Color = Color(0xFF777776),
    cool_gray_1cCp_900: Color = Color(0xFF5B5B5A),
    pure_white_50: Color = Color(0xFFFFFFFF),
    pure_white_100: Color = Color(0xFFFFFFFF),
    pure_white_200: Color = Color(0xFFFFFFFF),
    pure_white_300: Color = Color(0xFFFFFFFF),
    pure_white_400: Color = Color(0xFFFFFFFF),
    pure_white_500: Color = Color(0xFFFFFFFF),
    pure_white_600: Color = Color(0xFFE8E8E8),
    pure_white_700: Color = Color(0xFFB5B5B5),
    pure_white_800: Color = Color(0xFF8C8C8C),
    pure_white_900: Color = Color(0xFF6B6B6B),
    isLight: Boolean = true,
): AsphaltColors = AsphaltColors(
    gojek_green_50,
    gojek_green_100,
    gojek_green_200,
    gojek_green_300,
    gojek_green_400,
    gojek_green_500,
    gojek_green_600,
    gojek_green_700,
    gojek_green_800,
    gojek_green_900,
    payment_blue_50,
    payment_blue_100,
    payment_blue_200,
    payment_blue_300,
    payment_blue_400,
    payment_blue_500,
    payment_blue_600,
    payment_blue_700,
    payment_blue_800,
    payment_blue_900,
    partner_purple_50,
    partner_purple_100,
    partner_purple_200,
    partner_purple_300,
    partner_purple_400,
    partner_purple_500,
    partner_purple_600,
    partner_purple_700,
    partner_purple_800,
    partner_purple_900,
    retail_red_50,
    retail_red_100,
    retail_red_200,
    retail_red_300,
    retail_red_400,
    retail_red_500,
    retail_red_600,
    retail_red_700,
    retail_red_800,
    retail_red_900,
    odd_job_orange_50,
    odd_job_orange_100,
    odd_job_orange_200,
    odd_job_orange_300,
    odd_job_orange_400,
    odd_job_orange_500,
    odd_job_orange_600,
    odd_job_orange_700,
    odd_job_orange_800,
    odd_job_orange_900,
    popcorn_pink_50,
    popcorn_pink_100,
    popcorn_pink_200,
    popcorn_pink_300,
    popcorn_pink_400,
    popcorn_pink_500,
    popcorn_pink_600,
    popcorn_pink_700,
    popcorn_pink_800,
    popcorn_pink_900,
    black_50,
    black_100,
    black_200,
    black_300,
    black_400,
    black_500,
    black_600,
    black_700,
    black_800,
    black_900,
    sub_black_50,
    sub_black_100,
    sub_black_200,
    sub_black_300,
    sub_black_400,
    sub_black_500,
    sub_black_600,
    sub_black_700,
    sub_black_800,
    sub_black_900,
    cool_gray_50,
    cool_gray_100,
    cool_gray_200,
    cool_gray_300,
    cool_gray_400,
    cool_gray_500,
    cool_gray_600,
    cool_gray_700,
    cool_gray_800,
    cool_gray_900,
    cool_gray_1cCp_50,
    cool_gray_1cCp_100,
    cool_gray_1cCp_200,
    cool_gray_1cCp_300,
    cool_gray_1cCp_400,
    cool_gray_1cCp_500,
    cool_gray_1cCp_600,
    cool_gray_1cCp_700,
    cool_gray_1cCp_800,
    cool_gray_1cCp_900,
    pure_white_50,
    pure_white_100,
    pure_white_200,
    pure_white_300,
    pure_white_400,
    pure_white_500,
    pure_white_600,
    pure_white_700,
    pure_white_800,
    pure_white_900,
    isLight
)

fun darkAsphaltColors() = lightAsphaltColors(isLight = false)

@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color) =
    AsphaltTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

private fun AsphaltColors.contentColorFor(backgroundColor: Color): Color {
    return when (backgroundColor) {
        gojek_green_500 -> pure_white_500
        gojek_green_50 -> gojek_green_500
        payment_blue_500 -> pure_white_500
        payment_blue_50 -> payment_blue_500
        partner_purple_500 -> pure_white_500
        partner_purple_50 -> partner_purple_500
        retail_red_500 -> pure_white_500
        retail_red_50 -> retail_red_500
        odd_job_orange_500 -> pure_white_500
        odd_job_orange_50 -> odd_job_orange_500
        popcorn_pink_500 -> pure_white_500
        popcorn_pink_50 -> popcorn_pink_500
        black_500 -> pure_white_500
        black_50 -> black_500
        sub_black_500 -> pure_white_500
        sub_black_50 -> sub_black_500
        cool_gray_500 -> pure_white_500
        cool_gray_50 -> cool_gray_500
        cool_gray_1cCp_500 -> pure_white_500
        cool_gray_1cCp_50 -> cool_gray_1cCp_500
        pure_white_500 -> black_500
        pure_white_50 -> pure_white_500
        else -> Color.Unspecified
    }
}

@Composable
@ReadOnlyComposable
fun pressedColorFor(backgroundColor: Color) =
    AsphaltTheme.colors.pressedColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

private fun AsphaltColors.pressedColorFor(backgroundColor: Color): Color {
    return when (backgroundColor) {
        gojek_green_500 -> gojek_green_700
        gojek_green_50 -> gojek_green_100
        payment_blue_500 -> payment_blue_700
        payment_blue_50 -> payment_blue_100
        partner_purple_500 -> partner_purple_700
        partner_purple_50 -> partner_purple_100
        retail_red_500 -> retail_red_700
        retail_red_50 -> retail_red_100
        odd_job_orange_500 -> odd_job_orange_700
        odd_job_orange_50 -> odd_job_orange_100
        popcorn_pink_500 -> popcorn_pink_700
        popcorn_pink_50 -> popcorn_pink_100
        black_500 -> black_700
        black_50 -> black_100
        sub_black_500 -> sub_black_700
        sub_black_50 -> sub_black_100
        cool_gray_500 -> cool_gray_700
        cool_gray_50 -> cool_gray_100
        cool_gray_1cCp_500 -> cool_gray_1cCp_700
        cool_gray_1cCp_50 -> cool_gray_1cCp_100
        pure_white_500 -> pure_white_700
        pure_white_50 -> pure_white_100
        else -> Color.Unspecified
    }
}

internal val LocalColors = staticCompositionLocalOf { lightAsphaltColors() }

internal val LocalContentColor = compositionLocalOf { Black500 }

internal val LocalContentAlpha = compositionLocalOf { 1f }