package project.e_buyankina.feature.finances.ui

internal sealed interface News {

    class OpenRoute(val route: String) : News
}