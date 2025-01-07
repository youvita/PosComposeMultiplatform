package ui.parking.presentation

import ui.parking.domain.model.Parking

sealed class ParkingEvent {
    class SearchParking(val keyword: String): ParkingEvent()
    class AddParking(val parking: Parking) : ParkingEvent()
    class UpdateParking(val parking: Parking) : ParkingEvent()
    class GetParking() : ParkingEvent()
}