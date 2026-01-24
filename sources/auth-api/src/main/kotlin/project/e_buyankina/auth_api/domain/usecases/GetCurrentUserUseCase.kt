package project.e_buyankina.auth_api.domain.usecases

import project.e_buyankina.auth_api.domain.ProfileInfo

interface GetCurrentUserUseCase {

    suspend operator fun invoke(): ProfileInfo?
}