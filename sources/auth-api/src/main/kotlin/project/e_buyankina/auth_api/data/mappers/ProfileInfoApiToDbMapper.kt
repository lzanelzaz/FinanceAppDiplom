package project.e_buyankina.auth_api.data.mappers

import project.e_buyankina.auth_api.data.api.ProfileInfoApi
import project.e_buyankina.auth_api.data.db.ProfileInfoDb

internal interface ProfileInfoApiToDbMapper : (String, ProfileInfoApi) -> ProfileInfoDb

internal class ProfileInfoApiToDbMapperImpl : ProfileInfoApiToDbMapper {
    override fun invoke(
        accountId: String,
        api: ProfileInfoApi
    ): ProfileInfoDb {
        return ProfileInfoDb(
            accountId = accountId,
            username = api.username,
            email = api.email,
        )
    }
}