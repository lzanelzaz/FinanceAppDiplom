package project.e_buyankina.auth_api.domain.usecases

import project.e_buyankina.auth_api.domain.ProfileInfo

interface AuthorizeUseCase {

    suspend operator fun invoke(
        login: String,
        password: String,
    ): ProfileInfo
}