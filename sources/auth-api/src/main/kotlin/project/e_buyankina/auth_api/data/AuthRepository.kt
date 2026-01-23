package project.e_buyankina.auth_api.data

import project.e_buyankina.auth_api.data.api.AuthService
import project.e_buyankina.auth_api.data.db.ProfileInfoDao
import project.e_buyankina.auth_api.data.mappers.ProfileInfoApiToDbMapper
import project.e_buyankina.auth_api.data.mappers.ProfileInfoDbToDomainMapper
import project.e_buyankina.auth_api.domain.ProfileInfo

internal interface AuthRepository {

    fun createUser(
        login: String,
        password: String,
        username: String,
    ): ProfileInfo

    fun authorize(
        login: String,
        password: String,
    ): ProfileInfo

    fun profileInfo(
        accountId: String,
    ): ProfileInfo

    fun currentUser(): ProfileInfo?

    fun logOut()
}

internal class AuthRepositoryImpl(
    private val service: AuthService,
    private val dao: ProfileInfoDao,
    private val profileInfoApiToDbMapper: ProfileInfoApiToDbMapper,
    private val profileInfoDbToDomainMapper: ProfileInfoDbToDomainMapper,
) : AuthRepository {

    override fun createUser(
        login: String,
        password: String,
        username: String
    ): ProfileInfo {
        val accountId = service.createUser(login, password, username).accountId
        val api = service.profileInfo(accountId)
        val db = profileInfoApiToDbMapper(accountId, api)
        dao.insert(db)
        return profileInfoDbToDomainMapper(db)
    }

    override fun authorize(login: String, password: String): ProfileInfo {
        val accountId = service.authorize(login, password).accountId
        val api = service.profileInfo(accountId)
        val db = profileInfoApiToDbMapper(accountId, api)
        dao.insert(db)
        return profileInfoDbToDomainMapper(db)
    }

    override fun profileInfo(accountId: String): ProfileInfo {
        val api = service.profileInfo(accountId)
        val db = profileInfoApiToDbMapper(accountId, api)
        dao.insert(db)
        return profileInfoDbToDomainMapper(db)
    }

    override fun currentUser(): ProfileInfo? {
        val db = dao.getCurrentUser()
        return db?.let { profileInfoDbToDomainMapper(it) }
    }

    override fun logOut() {
        dao.delete()
    }
}