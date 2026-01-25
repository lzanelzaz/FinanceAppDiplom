package project.e_buyankina.splashscreen

internal sealed interface News {

    object OpenAuthScreen : News
    object OpenMainScreen : News
}