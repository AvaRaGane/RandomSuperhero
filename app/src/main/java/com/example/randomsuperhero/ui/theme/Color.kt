package com.example.randomsuperhero.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Tummansininen tausta ja kultainen teksti
val backRound1 = Color(0xFF001F3F) // Tummansininen
val text1 = Color(0xFFFFD700) // Kultainen

// Musta tausta ja neonvihreä tai neonoranssi teksti
val backRound2 = Color(0xFF000000) // Musta
val neonGreenText = Color(0xFF39FF14) // Neonvihreä
val neonOrangeText = Color(0xFFFF5F1F) // Neonoranssi

// Punainen tausta ja valkoinen teksti
val backRound3 = Color(0xFFFF4136) // Punainen
val whiteText = Color(0xFFFFFFFF) // Valkoinen


var backRoundColouri by mutableStateOf(backRound1)
var fontColouri by mutableStateOf(text1)