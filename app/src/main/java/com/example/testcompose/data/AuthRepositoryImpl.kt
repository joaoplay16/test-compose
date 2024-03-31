package com.example.testcompose.data

import com.example.testcompose.domain.AuthRepository
import com.example.testcompose.domain.DataError
import com.example.testcompose.domain.User
import com.example.testcompose.domain.Result
import retrofit2.HttpException

class AuthRepositoryImpl: AuthRepository {

    override suspend fun register(password: String): Result<User, DataError.Network> {
        // API call logic
        return try {
            val user = User("dummy", "dummy", "dummy")
            Result.Success(user)
        } catch(e: HttpException) { // HttpException
            when(e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}