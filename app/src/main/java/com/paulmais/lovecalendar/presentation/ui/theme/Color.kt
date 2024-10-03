package com.paulmais.lovecalendar.presentation.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Cameo = Color(0xFFDDBEA7)
val Iron = Color(0xFFE4E6E8)
val Perano = Color(0xFFAACEF5)
val Sidecar = Color(0xFFF3DFC1)
val Sindbad = Color(0xFFA1D2CD)
val WildSand = Color(0xFFF5F5F5)
val Cornflower_Lilac = Color(0xFFFFACAC)

val pastMeetingColor = Perano
val futureMeetingColor = Sindbad
val specialColor = Cornflower_Lilac

val appLightColors = lightColorScheme(
    primary = Sidecar,
    secondary = Cameo,
    background = Iron,
    surface = WildSand,
    onSurface = Color.Black
)

val appDarkColors = appLightColors