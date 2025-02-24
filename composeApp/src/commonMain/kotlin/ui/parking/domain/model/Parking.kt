package ui.parking.domain.model

data class Parking(
    val id: Long = 0,
    val parkingNo: String? = null,
    val checkIn: String? = null,
    val checkOut: String? = null,
    val duration: Int? = null,
    val timeUnit: String? = null,
    val fee: Double? = null,
    val total: Double? = null,
    val status: String? = null
)
