package main.model

import androidx.compose.ui.graphics.painter.Painter

data class NavModel(
    val id: Int,
    val icon: Painter,
    val label: String
)
