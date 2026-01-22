package project.e_buyankina.auth_api.data

import project.e_buyankina.auth_api.domain.AuthorizeInfo
import project.e_buyankina.auth_api.domain.ProfileInfo

internal interface AuthRepository {

    fun createUser(
        login: String,
        password: String,
        username: String,
    ): AuthorizeInfo

    fun authorize(
        login: String,
        password: String,
    ): AuthorizeInfo

    fun profileInfo(
        accountId: String,
    ): ProfileInfo
}

internal class AuthRepositoryImpl(
    private val service: AuthService,

    ) : AuthRepository {

    override fun createUser(
        login: String,
        password: String,
        username: String
    ) {
        return service.createUser(login, password, username)
    }

    override fun authorize(login: String, password: String) {
        return service.authorize(login, password)
    }

    override fun profileInfo(accountId: String): ProfileInfoApi {
        return service.profileInfo(accountId)
    }
}