package project.e_buyankina.auth_api.data.usecases

import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.domain.ProfileInfo
import project.e_buyankina.auth_api.domain.usecases.AuthorizeUseCase

internal class AuthorizeUseCaseImpl(
    private val repository: AuthRepository,
) : AuthorizeUseCase {

    override suspend fun invoke(
        login: String,
        password: String,
    ): ProfileInfo {
        return repository.authorize(login, password)
    }
}