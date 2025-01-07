package ui.parking.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.parking.domain.model.Parking
import ui.parking.domain.repository.ParkingRepository

data class ParkingState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<Parking>? = null
)

class ParkingViewModel(
    private val repository: ParkingRepository
): ScreenModel {

    private val _state = MutableStateFlow(ParkingState())
    val state: StateFlow<ParkingState> = _state.asStateFlow()

    init {
        onGetParking()
    }

    fun onEvent(event: ParkingEvent) {
        when(event) {
            is ParkingEvent.SearchParking -> {
                onSearchParking(event.keyword)
            }
            is ParkingEvent.GetParking -> {
                onGetParking()
            }
            is ParkingEvent.AddParking -> {
                onCheckIn(event.parking)
            }
            is ParkingEvent.UpdateParking -> {
                onCheckOut(event.parking)
            }
        }
    }

    private fun onCheckIn(parking: Parking) {
        screenModelScope.launch {
            repository.addParking(parking)
        }
    }

    private fun onCheckOut(parking: Parking) {
        screenModelScope.launch {
            repository.updateParking(parking)
        }
    }

    private fun onGetParking() {
        screenModelScope.launch {
            repository.getAllParking().collect { parking ->
                _state.value = _state.value.copy(
                    data = parking.data
                )
            }
        }
    }

    private fun onSearchParking(parkingNo: String) {
        screenModelScope.launch {
            repository.searchParking(parkingNo).collect { parking ->
                _state.value = _state.value.copy(
                    data = parking.data
                )
            }
        }
    }
}