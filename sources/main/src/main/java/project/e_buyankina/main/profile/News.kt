package project.e_buyankina.main.profile

internal sealed interface News {

    class OpenRoute(val route: String) : News
}