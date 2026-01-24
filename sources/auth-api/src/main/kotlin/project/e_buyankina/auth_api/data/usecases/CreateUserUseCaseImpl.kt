package project.e_buyankina.auth_api.data.usecases

import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.domain.ProfileInfo
import project.e_buyankina.auth_api.domain.usecases.CreateUserUseCase

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