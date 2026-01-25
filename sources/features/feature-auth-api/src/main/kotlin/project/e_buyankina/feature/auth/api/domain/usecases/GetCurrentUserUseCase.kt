package project.e_buyankina.feature.auth.api.domain.usecases

import project.e_buyankina.feature.auth.api.data.AuthRepository
import project.e_buyankina.feature.auth.api.domain.ProfileInfo

interface GetCurrentUserUseCase {

    suspend operator fun invoke(): ProfileInfo?
}

internal class GetCurrentUserUseCaseImpl(
    private val repository: AuthRepository,
) : GetCurrentUserUseCase {

    override suspend fun invoke(): ProfileInfo? {
        return repository.currentUser()
    }
}