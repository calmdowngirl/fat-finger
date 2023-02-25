package com.calmdowngirl.fatfinger.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.calmdowngirl.fatfinger.R

@OptIn(ExperimentalTextApi::class)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
@OptIn(ExperimentalTextApi::class)
val lobster2 = GoogleFont("Lobster Two")
@OptIn(ExperimentalTextApi::class)
val fontFamilyLobster2 = FontFamily(
    Font(
        googleFont = lobster2,
        fontProvider = provider,
        weight = FontWeight.ExtraLight,
    )
)

val gajrajOne = FontFamily(Font(R.font.gajraj_one_regular))
val tiltPrism = FontFamily(Font(R.font.tilt_prism_regular_variable_font_xrot_yrot))

/*
@OptIn(ExperimentalTextApi::class)
val tiltPrism = GoogleFont("Tilt Prism")

@OptIn(ExperimentalTextApi::class)
val gajrajOne = GoogleFont("Gajraj One")

@OptIn(ExperimentalTextApi::class)
val fontFamilyTiltPrism = FontFamily(
    Font(googleFont = tiltPrism, fontProvider = provider)
)

@OptIn(ExperimentalTextApi::class)
val fontFamilyGajrajOne = FontFamily(
    Font(googleFont = gajrajOne, fontProvider = provider)
)
*/

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)