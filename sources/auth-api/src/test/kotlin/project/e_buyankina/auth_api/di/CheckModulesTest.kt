package project.e_buyankina.auth_api.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
internal class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        authApiModule.verify()
    }
}