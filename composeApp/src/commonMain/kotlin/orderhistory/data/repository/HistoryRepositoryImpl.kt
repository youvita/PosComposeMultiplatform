package orderhistory.data.repository

import core.data.Resource

import orderhistory.domain.repository.OrderHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.topteam.pos.OrderEntity
import org.topteam.pos.PosDatabase
import org.topteam.pos.ProductOrderEntity


class HistoryRepositoryImpl(
    posDatabase: PosDatabase
): OrderHistoryRepository {

    private val db = posDatabase.appDatabaseQueries
    override fun addOrderHistory(orderEntity: OrderEntity): Flow<Resource<Unit>> {
        val dataInput = db.insertOrderHistory(
            order_no = orderEntity.order_no,
            queue_no = orderEntity.queue_no,
            date = orderEntity.date,
            total_item = orderEntity.total_item,
            total_qty = orderEntity.total_qty,
            sub_total = orderEntity.sub_total,
            discount = orderEntity.discount,
            vat = orderEntity.vat,
            total = orderEntity.total,
            status = orderEntity.status
        )
        return flow {
            emit(Resource.Success(dataInput))
        }
    }

    override fun addProductOrder(listProductOrderEntity: List<ProductOrderEntity>): Flow<Resource<Unit>> {
        val dataInput = listProductOrderEntity.forEach {
            db.insertProductOrder(
                product_id = it.product_id, //product code
                order_id = it.order_id,
                name = it.name,
                image = it.image,
                qty = it.qty,
                price = it.price,
                discount = it.discount
            )
        }
        return flow {
            emit(Resource.Success(dataInput))
        }
    }

    override fun getOrderHistory(): Flow<Resource<List<OrderEntity>>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(db.getAllOrderHistory().executeAsList()))
        }
    }

    override fun getOrderHistoryPaging(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<OrderEntity>>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(db.getOrderHistoryPaging(limit.toLong(), offset.toLong()).executeAsList()))
        }
    }

    override fun getProductByOrderId(id: Long): Flow<Resource<List<ProductOrderEntity>>> = flow {
        emit(Resource.Loading())
        val dataOutput = db.getProdutByOrderId(id).executeAsList()
        emit(Resource.Success(dataOutput))
    }
}