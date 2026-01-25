package project.e_buyankina.feature.profile.ui

internal sealed interface News {

    class OpenRoute(val route: String) : News
}