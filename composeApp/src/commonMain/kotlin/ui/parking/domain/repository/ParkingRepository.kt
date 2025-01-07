package ui.parking.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import ui.parking.domain.model.Parking

interface ParkingRepository {

    suspend fun addParking(parking: Parking)

    suspend fun updateParking(parking: Parking)

    suspend fun getAllParking(): Flow<Resource<List<Parking>>>

    suspend fun searchParking(parkingNo: String): Flow<Resource<List<Parking>>>
}