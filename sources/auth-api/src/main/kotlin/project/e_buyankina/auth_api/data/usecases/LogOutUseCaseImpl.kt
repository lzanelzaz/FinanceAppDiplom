package project.e_buyankina.auth_api.data.usecases

import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.domain.usecases.LogOutUseCase

internal class LogOutUseCaseImpl(
    private val repository: AuthRepository,
) : LogOutUseCase {

    override suspend fun invoke() {
        return repository.logOut()
    }
}