package project.e_buyankina.feature.auth.api.domain.usecases

import project.e_buyankina.feature.auth.api.data.AuthRepository

interface LogOutUseCase {

    suspend operator fun invoke()
}

internal class LogOutUseCaseImpl(
    private val repository: AuthRepository,
) : LogOutUseCase {

    override suspend fun invoke() {
        return repository.logOut()
    }
}