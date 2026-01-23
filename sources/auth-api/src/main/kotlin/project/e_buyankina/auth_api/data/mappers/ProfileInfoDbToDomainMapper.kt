package project.e_buyankina.auth_api.data.mappers

import project.e_buyankina.auth_api.data.db.ProfileInfoDb
import project.e_buyankina.auth_api.domain.ProfileInfo

internal interface ProfileInfoDbToDomainMapper : (ProfileInfoDb) -> ProfileInfo

internal class ProfileInfoDbToDomainMapperImpl : ProfileInfoDbToDomainMapper {

    override fun invoke(db: ProfileInfoDb): ProfileInfo {
        return ProfileInfo(
            accountId = db.accountId,
            username = db.username,
            email = db.email,
        )
    }
}