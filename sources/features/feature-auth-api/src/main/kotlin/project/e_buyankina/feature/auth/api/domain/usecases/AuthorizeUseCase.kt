package project.e_buyankina.feature.auth.api.domain.usecases

import project.e_buyankina.feature.auth.api.data.AuthRepository
import project.e_buyankina.feature.auth.api.domain.ProfileInfo

interface AuthorizeUseCase {

    suspend operator fun invoke(
        login: String,
        password: String,
    ): ProfileInfo
}

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