package login.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(username: String, password: String): Flow<Resource<Boolean>>
}