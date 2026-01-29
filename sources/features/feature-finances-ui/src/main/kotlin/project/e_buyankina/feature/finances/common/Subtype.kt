package project.e_buyankina.feature.finances.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import project.e_buyankina.feature.finances.R

internal sealed interface Subtype {
    val code: String

    @get:DrawableRes
    val icon: Int

    @get:StringRes
    val text: Int

    enum class Expense(
        override val code: String,
        @param:DrawableRes override val icon: Int,
        @param:StringRes override val text: Int,
    ) : Subtype {
        ENTERTAINMENT("Развлечения", R.drawable.confirmation_number_24dp, R.string.entertainment),
        GROCERIES("Продукты", R.drawable.shopping_cart_24dp, R.string.groceries),
        CAFE("Кафе", R.drawable.bakery_dining_24dp, R.string.cafe),
        TRANSPORT("Транспорт", R.drawable.boat_bus_24dp, R.string.transport),
        HOUSEHOLD("Бытовые", R.drawable.cottage_24dp, R.string.household),
        CLOTHING("Одежда", R.drawable.apparel_24dp, R.string.clothing),
        GIFTS("Подарки", R.drawable.featured_seasonal_and_gifts_24dp, R.string.gifts),
        MANDATORY("Обязательные", R.drawable.receipt_long_24dp, R.string.mandatory),
        BEAUTY("Красота", R.drawable.fragrance_24dp, R.string.beauty),
    }

    enum class Income(
        override val code: String,
        @param:DrawableRes override val icon: Int,
        @param:StringRes override val text: Int,
    ) : Subtype {
        SALARY("Зарплата", R.drawable.payments_24dp, R.string.salary),
        INTEREST("Проценты", R.drawable.savings_24dp, R.string.interest),
        TRANSFERS("Переводы", R.drawable.sync_alt_24dp, R.string.transfers)
    }

    companion object {

        val all: List<Subtype> = Expense.entries + Income.entries

        fun List<Subtype>.findByCode(code: String) = find { it.code == code }
    }
}

