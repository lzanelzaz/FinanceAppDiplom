package project.e_buyankina.feature.analytics.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import project.e_buyankina.common.ui.preview.DayNightPreviews
import project.e_buyankina.common.ui.theme.AppTheme
import project.e_buyankina.feature.analytics.R
import java.math.BigDecimal

@Composable
fun PieChart(
    chartData: Map<String, BigDecimal>,
    modifier: Modifier = Modifier,
    defaultColorList: List<Color> = PieChartDefaults.defaultColorList(),
    radius: Dp = PieChartDefaults.radius(),
    thickness: Dp = PieChartDefaults.thickness(),
) {
    val totalSum = chartData.values.sumOf { it }
    val entries = chartData.entries.mapIndexed { index, entry ->
        PieChartEntry(
            name = entry.key,
            amount = entry.value,
            percent = BigDecimal(100) * entry.value / totalSum,
            arc = 360 * entry.value.toFloat() / totalSum.toFloat(),
            color = defaultColorList.getOrElse(index) { defaultColorList[index % defaultColorList.size].copy(alpha = 0.5f) }
        )
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.pie_chart_title, totalSum.toString()),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Box(modifier = Modifier.padding(32.dp)) {
            Canvas(modifier = Modifier.size(radius * 2f)) {
                var lastValue = 0f
                entries.forEach { entry ->
                    drawArc(
                        color = entry.color,
                        startAngle = lastValue,
                        sweepAngle = entry.arc,
                        useCenter = false,
                        style = Stroke(thickness.toPx()),
                    )
                    lastValue += entry.arc
                }
            }
        }
        PieChartItems(
            data = entries,
            scrollState = scrollState,
        )
    }
}

@Composable
private fun PieChartItems(
    data: List<PieChartEntry>,
    scrollState: ScrollState,
) {
    FlowRow(modifier = Modifier.verticalScroll(state = scrollState)) {
        data.sortedByDescending { it.percent }.forEach { item ->
            val percent = item.percent.setScale(2).toString()
            val amount = item.amount.setScale(2).toString()
            val text = item.name
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .background(color = item.color.copy(alpha = 0.5f), shape = CircleShape)
                    .padding(8.dp),
                text = "$percent% $text: $amount ₽",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

private data class PieChartEntry(
    val name: String,
    val amount: BigDecimal,
    val percent: BigDecimal,
    val arc: Float,
    val color: Color
)

@Stable
object PieChartDefaults {

    fun defaultColorList(): List<Color> = listOf(
        Color(0xFF519DE9),
        Color(0xFF007BFF),
        Color(0xFF03DAC5),
        Color(0xFF625CEE),
        Color(0xFFF7B7A3),
        Color(0xFFEA5F89),
        Color(0xFF9B3192),
        Color(0xFF2954A1),
    )

    fun radius(): Dp = 60.dp

    fun thickness(): Dp = 20.dp
}

@DayNightPreviews
@Composable
private fun Preview() {
    val chartData = mapOf(
        "Развлечения" to BigDecimal("15670.00"),
        "Продукты" to BigDecimal("23450.50"),
        "Кафе" to BigDecimal("8730.75"),
        "Транспорт" to BigDecimal("5430.25"),
        "Бытовые" to BigDecimal("12890.00"),
        "Одежда" to BigDecimal("18700.30"),
        "Подарки" to BigDecimal("5600.00"),
        "Обязательные" to BigDecimal("31200.80"),
        "Красота" to BigDecimal("9450.50"),
    )
    AppTheme {
        PieChart(
            modifier = Modifier.fillMaxWidth(),
            chartData = chartData,
        )
    }
}