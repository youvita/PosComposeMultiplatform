import androidx.compose.runtime.Composable

interface Platform {
    val name: String
    @Composable
    fun print()
}

expect fun getPlatform(): Platform