package ui.parking.data.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.topteam.pos.PosDatabase
import ui.parking.domain.model.Parking
import ui.parking.domain.repository.ParkingRepository

class ParkingRepositoryImpl(posDatabase: PosDatabase): ParkingRepository {

    private val db = posDatabase.appDatabaseQueries

    override suspend fun addParking(parking: Parking) {
        val result = db.getParkingById(parking.parkingNo).executeAsList()
        if (result.isEmpty()) {
            db.insertParking(
                parking_no = parking.parkingNo,
                check_in = parking.checkIn,
                check_out = null,
                duration = 0,
                total = 0
            )
        }
    }

    override suspend fun updateParking(parking: Parking) {
        val result = db.getParkingById(parking.parkingNo).executeAsList()
        if (result.isNotEmpty()) {
            db.updateParking(
                parking_no = parking.parkingNo,
                check_out = parking.checkOut,
                duration = parking.duration?.toLong(),
                total = parking.total?.toLong()
            )
        }
    }

    override suspend fun getAllParking(): Flow<Resource<List<Parking>>> = flow {
        emit(Resource.Loading())
        val result = db.getParking().executeAsList()
        val parking = mutableListOf<Parking>()
        for (item in result) {
            val match = Parking(
                id = item.id,
                parkingNo = item.parking_no,
                checkIn = item.check_in,
                checkOut = item.check_out,
                duration = item.duration?.toInt(),
                total = item.total?.toInt(),
            )
            parking.add(match)
        }
        emit(Resource.Success(parking))
    }

    override suspend fun searchParking(parkingNo: String): Flow<Resource<List<Parking>>> = flow {
        emit(Resource.Loading())
        val result = db.getParkingById(parkingNo).executeAsList()
        val parking = mutableListOf<Parking>()
        for (item in result) {
            val match = Parking(
                id = item.id,
                parkingNo = item.parking_no,
                checkIn = item.check_in,
                checkOut = item.check_out,
                duration = item.duration?.toInt(),
                total = item.total?.toInt(),
            )
            parking.add(match)
        }
        emit(Resource.Success(parking))
    }
}