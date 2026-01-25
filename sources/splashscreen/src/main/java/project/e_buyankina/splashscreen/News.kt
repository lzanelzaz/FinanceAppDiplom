package project.e_buyankina.splashscreen

internal sealed interface News {

    class OpenRoute(val route: String) : News
}