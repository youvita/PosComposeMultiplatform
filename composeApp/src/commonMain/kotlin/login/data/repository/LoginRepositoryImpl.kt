package login.data.repository

import core.data.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import login.domain.repository.LoginRepository

class LoginRepositoryImpl: LoginRepository {
    override fun login(username: String, password: String): Flow<Resource<Boolean>> {
        Resource.Loading(true)
        return flow {
            delay(1000)
            Resource.Success(true)
        }
    }
}