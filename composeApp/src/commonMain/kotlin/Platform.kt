interface Platform {
    val name: String
    fun print()
}

expect fun getPlatform(): Platform