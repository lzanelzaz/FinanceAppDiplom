package project.e_buyankina.feature.auth.api.data

import project.e_buyankina.common.network.retrofitErrorHandler
import project.e_buyankina.feature.auth.api.data.api.AuthService
import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDao
import project.e_buyankina.feature.auth.api.data.mappers.ProfileInfoApiToDbMapper
import project.e_buyankina.feature.auth.api.data.mappers.ProfileInfoDbToDomainMapper
import project.e_buyankina.feature.auth.api.domain.ProfileInfo

internal interface AuthRepository {

    suspend fun createUser(
        login: String,
        password: String,
        username: String,
    ): ProfileInfo

    suspend fun authorize(
        login: String,
        password: String,
    ): ProfileInfo

    suspend fun currentUser(): ProfileInfo?

    suspend fun logOut()
}

internal class AuthRepositoryImpl(
    private val service: AuthService,
    private val dao: ProfileInfoDao,
    private val profileInfoApiToDbMapper: ProfileInfoApiToDbMapper,
    private val profileInfoDbToDomainMapper: ProfileInfoDbToDomainMapper,
) : AuthRepository {

    override suspend fun createUser(
        login: String,
        password: String,
        username: String
    ): ProfileInfo {
        val accountId =
            retrofitErrorHandler(service.createUser(login, password, username)).accountId
        return getProfileInfo(accountId)
    }

    override suspend fun authorize(login: String, password: String): ProfileInfo {
        val accountId = retrofitErrorHandler(service.authorize(login, password)).accountId
        return getProfileInfo(accountId)
    }

    override suspend fun currentUser(): ProfileInfo? {
        val db = dao.getCurrentUser()
        return db?.let { profileInfoDbToDomainMapper(it) }
    }

    override suspend fun logOut() {
        dao.delete()
    }

    private suspend fun getProfileInfo(accountId: String): ProfileInfo {
        val api = retrofitErrorHandler(service.profileInfo(accountId))
        val db = profileInfoApiToDbMapper(accountId, api)
        dao.insert(db)
        return profileInfoDbToDomainMapper(db)
    }
}