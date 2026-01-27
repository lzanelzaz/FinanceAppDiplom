package project.e_buyankina.feature.finances.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import project.e_buyankina.feature.finances.R

internal interface Subtype {
    val index: Int
    @get:DrawableRes
    val icon: Int
    @get:StringRes
    val text: Int
}

internal enum class Expense(
    override val index: Int,
    @param:DrawableRes override val icon: Int,
    @param:StringRes override val text: Int,
) : Subtype {
    ENTERTAINMENT(0, R.drawable.confirmation_number_24dp, R.string.entertainment),
    GROCERIES(1, R.drawable.shopping_cart_24dp, R.string.groceries),
    CAFE(2, R.drawable.bakery_dining_24dp, R.string.cafe),
    TRANSPORT(3, R.drawable.boat_bus_24dp, R.string.transport),
    HOUSEHOLD(4, R.drawable.cottage_24dp, R.string.household),
    CLOTHING(5, R.drawable.apparel_24dp, R.string.clothing),
    GIFTS(6, R.drawable.featured_seasonal_and_gifts_24dp, R.string.gifts),
    MANDATORY(7, R.drawable.receipt_long_24dp, R.string.mandatory),
    BEAUTY(8, R.drawable.fragrance_24dp, R.string.beauty),
}

internal enum class Income(
    override val index: Int,
    @param:DrawableRes override val icon: Int,
    @param:StringRes override val text: Int,
) : Subtype {
    SALARY(9, R.drawable.payments_24dp, R.string.salary),
    INTEREST(10, R.drawable.savings_24dp, R.string.interest),
    TRANSFERS(11, R.drawable.sync_alt_24dp, R.string.transfers)
}