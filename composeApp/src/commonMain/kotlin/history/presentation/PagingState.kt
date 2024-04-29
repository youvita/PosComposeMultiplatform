package history.presentation

import core.data.Status

data class PagingState(
    val status: Status? = null,
    val isLoading: Boolean = false,
    val offset: Int = 0,
    val totalPage: Int = 1,
    val allRow: Int? = 0,
    val currentPage: Int = 0,
    val limitRow: Int = 5,
)