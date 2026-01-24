package project.e_buyankina.auth_api.data.usecases

import project.e_buyankina.auth_api.data.AuthRepository
import project.e_buyankina.auth_api.domain.ProfileInfo
import project.e_buyankina.auth_api.domain.usecases.GetCurrentUserUseCase

internal class GetCurrentUserUseCaseImpl(
    private val repository: AuthRepository,
) : GetCurrentUserUseCase {

    override suspend fun invoke(): ProfileInfo? {
        return repository.currentUser()
    }
}