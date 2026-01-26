package project.e_buyankina.feature.auth.api.domain.usecases

import project.e_buyankina.feature.auth.api.data.AuthRepository

interface ClearProfileInfoUseCase {

    suspend operator fun invoke()
}

internal class ClearProfileInfoUseCaseImpl(
    private val repository: AuthRepository,
) : ClearProfileInfoUseCase {

    override suspend fun invoke() {
        return repository.logOut()
    }
}