package project.e_buyankina.feature.auth.api.domain.usecases

import project.e_buyankina.feature.auth.api.data.AuthRepository
import project.e_buyankina.feature.auth.api.domain.ProfileInfo

interface CreateUserUseCase {

    suspend operator fun invoke(
        login: String,
        password: String,
        username: String,
    ): ProfileInfo
}

internal class CreateUserUseCaseImpl(
    private val repository: AuthRepository,
) : CreateUserUseCase {

    override suspend fun invoke(
        login: String,
        password: String,
        username: String,
    ): ProfileInfo {
        return repository.createUser(login, password, username)
    }
}