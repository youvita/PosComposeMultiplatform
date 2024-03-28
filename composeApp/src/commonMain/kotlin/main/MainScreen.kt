package main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class MainScreen(): Screen {
    @Composable
    override fun Content() {
        Text("Main")
    }

}