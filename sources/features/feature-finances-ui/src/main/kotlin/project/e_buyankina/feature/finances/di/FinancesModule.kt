package project.e_buyankina.feature.finances.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import project.e_buyankina.common.navigation.features.FinancesNavigation
import project.e_buyankina.feature.finances.create_edit_operation.CreateOrEditOperationViewModel
import project.e_buyankina.feature.finances.navigation.FinancesNavigationImpl
import project.e_buyankina.feature.finances.ui.FinancesViewModel

val financesModule = module {
    viewModelOf(::FinancesViewModel)
    viewModelOf(::CreateOrEditOperationViewModel)
    singleOf(::FinancesNavigationImpl) { bind<FinancesNavigation>() }
}
