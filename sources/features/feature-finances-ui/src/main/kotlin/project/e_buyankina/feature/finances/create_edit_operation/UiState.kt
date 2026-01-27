package project.e_buyankina.feature.finances.create_edit_operation

import androidx.annotation.DrawableRes
import project.e_buyankina.feature.finances.common.Subtype
import project.e_buyankina.feature.finances.common.Type

internal data class UiState(
    val selectedDate: String,
    val selectedType: Type,
    val selectedSubtype: Subtype,
    val types: List<Type>,
    val subtypes: List<Subtype>,
    val amount: String,
    val keyboard: List<KeyBoardItem>,
) {

    sealed interface KeyBoardItem {

        data class Digit(val digit: Int) : KeyBoardItem

        data class Point(val char: String) : KeyBoardItem

        data class Backspace(@param:DrawableRes val icon: Int) : KeyBoardItem
    }
}