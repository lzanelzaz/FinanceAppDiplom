package project.e_buyankina.feature.auth.api.data.mappers

import project.e_buyankina.feature.auth.api.data.db.ProfileInfoDb
import project.e_buyankina.feature.auth.api.domain.ProfileInfo

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