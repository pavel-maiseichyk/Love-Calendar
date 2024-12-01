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
val todayColor = Sidecar

val red = Color(0xFFD3455A)
val blue = Color(0xFF2D9BD2)
val green = Color(0xFF59AB39)
val yellow = Color(0xFFCDB332)

val red_container = Color(0xFFF7D4DA)
val blue_container = Color(0xFFD4EBF7)
val green_container = Color(0xFFDFF8D7)
val yellow_container = Color(0xFFF7F1D4)

val dark_gray = Color(0xFF2C2C2C)
val light_gray = Color(0xFFE1E1E1)

val appLightColors = lightColorScheme(
    primary = Sidecar,
    secondary = Cameo,
    background = Iron,
    surface = WildSand,
    onSurface = Color.Black
)

val appDarkColors = appLightColors