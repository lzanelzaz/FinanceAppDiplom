package project.e_buyankina.feature.auth.ui

internal sealed interface News {

    class OpenRoute(val route: String) : News
}