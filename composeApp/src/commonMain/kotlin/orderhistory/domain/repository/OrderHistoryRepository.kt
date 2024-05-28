package orderhistory.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.OrderEntity
import org.topteam.pos.ProductOrderEntity

interface OrderHistoryRepository {
     fun addOrderHistory(orderEntity: OrderEntity): Flow<Resource<Unit>>
     fun addProductOrder(listProductOrderEntity: List<ProductOrderEntity>): Flow<Resource<Unit>>
     fun getOrderHistory(): Flow<Resource<List<OrderEntity>>>
     fun getOrderHistoryPaging(limit: Int, offset: Int): Flow<Resource<List<OrderEntity>>>
}
