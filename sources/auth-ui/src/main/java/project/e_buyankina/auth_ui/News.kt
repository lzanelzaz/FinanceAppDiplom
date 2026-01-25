package project.e_buyankina.auth_ui

internal sealed interface News {

    class ShowToast(val text: String?) : News
}