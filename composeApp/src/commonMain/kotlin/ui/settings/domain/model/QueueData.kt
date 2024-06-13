package ui.settings.domain.model

import core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class QueueData(
    val referId: Int = Constants.PreferenceType.QUEUE,
    val isUsed: Boolean = false,
    val queueNumber: Int? = null,
)
