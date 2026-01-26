package project.e_buyankina.feature.splashscreen.ui

internal sealed interface News {

    class OpenRoute(val route: String) : News
}