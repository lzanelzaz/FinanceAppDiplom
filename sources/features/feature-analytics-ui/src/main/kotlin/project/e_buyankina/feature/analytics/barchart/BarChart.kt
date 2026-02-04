package project.e_buyankina.feature.analytics.barchart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.common.ui.theme.moneyGreen
import project.e_buyankina.common.ui.theme.moneyRed
import project.e_buyankina.feature.analytics.R
import java.math.BigDecimal

@Composable
internal fun BarChart(
    chartData: Map<String, BarGroup>,
    modifier: Modifier = Modifier,
    incomeColor: Color = BarChartDefaults.incomeColor(),
    expenseColor: Color = BarChartDefaults.expenseColor(),
    barHeight: Dp = BarChartDefaults.barHeight(),
    barWidth: Dp = BarChartDefaults.barWidth(),
    gapBetweenBar: Dp = BarChartDefaults.gapBetweenBar(),
    gapBetweenGroup: Dp = BarChartDefaults.gapBetweenGroup(),
    shape: Shape = BarChartDefaults.shape(),
) {
    val maxValue = chartData.values.maxOf { maxOf(it.income, it.expense) }.toFloat()
    val entries = chartData.map { (month, group) ->
        BarChartItem(
            name = month,
            income = BarChartItem.Bar(
                amount = group.income,
                percent = group.income.toFloat() / maxValue,
                color = incomeColor
            ),
            expense = BarChartItem.Bar(
                amount = group.expense,
                percent = group.expense.toFloat() / maxValue,
                color = expenseColor
            ),
        )
    }
    LazyRow(modifier = modifier.wrapContentWidth(), horizontalArrangement = Arrangement.Center) {
        items(entries, key = { keyItem -> keyItem.name }) { barItem ->
            Column(
                modifier = Modifier.padding(horizontal = gapBetweenGroup / 2),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    listOf(barItem.income, barItem.expense).forEach { bar ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = gapBetweenBar / 2)
                                .height(barHeight)
                                .width(barWidth),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(shape = shape)
                                    .fillMaxHeight(bar.percent)
                                    .fillMaxWidth()
                                    .background(color = bar.color)
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(
                        R.string.bar_chart_item_title,
                        barItem.name,
                        barItem.income.amount,
                        barItem.expense.amount,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private data class BarChartItem(
    val name: String, val income: Bar, val expense: Bar
) {
    data class Bar(val amount: BigDecimal, val percent: Float, val color: Color)
}

data class BarGroup(
    val income: BigDecimal,
    val expense: BigDecimal,
)

object BarChartDefaults {

    fun incomeColor(): Color = moneyGreen.copy(alpha = 0.8f)

    fun expenseColor(): Color = moneyRed.copy(alpha = 0.8f)

    fun barHeight(): Dp = 200.dp

    fun barWidth(): Dp = 20.dp

    fun gapBetweenBar(): Dp = 4.dp

    fun gapBetweenGroup(): Dp = 16.dp

    fun shape(): Shape = RoundedCornerShape(4.dp)
}

@DayNightPreviews
@Composable
private fun Preview() {
    val groupData = mapOf(
        "авг." to BarGroup(BigDecimal("59000.35"), BigDecimal("46000.70")),
        "сен." to BarGroup(BigDecimal("53000.80"), BigDecimal("44000.20")),
        "окт." to BarGroup(BigDecimal("57000.45"), BigDecimal("47000.90")),
        "ноя." to BarGroup(BigDecimal("61000.25"), BigDecimal("49000.30")),
        "дек." to BarGroup(BigDecimal("65000.00"), BigDecimal("52000.75"))
    )

    AppTheme {
        BarChart(chartData = groupData)
    }

}