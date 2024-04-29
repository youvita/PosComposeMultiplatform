package history.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.History

interface HistoryRepository {
     fun getOrderHistory(): Flow<Resource<List<History>>>
     fun getOrderHistoryPaging(limit: Int, offset: Int): Flow<Resource<List<History>>>
//    suspend fun getOrderByOrderIDAmountCashier(string: String): Resource<OrderModel>
//    suspend fun getOrderDetail(id: Long): Resource<List<OrderDetail>>
}
