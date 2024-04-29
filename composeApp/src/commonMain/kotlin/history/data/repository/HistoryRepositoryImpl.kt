package history.data.repository

import core.data.Resource

import history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.topteam.pos.History
import org.topteam.pos.PosDatabase


class HistoryRepositoryImpl(
    posDatabase: PosDatabase
): HistoryRepository {

    private val db = posDatabase.appDatabaseQueries
    override fun getOrderHistory(): Flow<Resource<List<History>>> {
//        db.insertHistory()
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(db.getAllHistory().executeAsList()))
        }
    }

    override fun getOrderHistoryPaging(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<History>>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(db.getHistoryPaging(limit.toLong(), offset.toLong()).executeAsList()))
        }
    }

//    override suspend fun getOrderHistoryPaging(
//        limit: Int,
//        offset: Int
//    ): Resource<List<OrderModel>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getOrderByOrderIDAmountCashier(string: String): Resource<OrderModel> {
//        TODO("Not yet implemented")
//    }


}