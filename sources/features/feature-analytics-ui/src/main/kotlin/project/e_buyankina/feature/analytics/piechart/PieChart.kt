package project.e_buyankina.feature.analytics.piechart

import androidx.annotation.Keep
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.e_buyankina.common.ui.preview.DayNightPreviews
import java.text.DecimalFormat

@Keep
@Stable
data class PieChartConfig(
    val radius: Dp,
    val thickness: Dp,
    val colorsList: List<Color>,
    val enableChartItems: Boolean,
    val isChartItemScrollEnable: Boolean,
    val textStyle: TextStyle
)

/**
 * PieChartAnimationConfig is a data class used to configure the animation behavior of a Pie Chart.
 * It allows customization of whether animations are enabled, the duration of the animation,
 * and the number of rotations the chart undergoes during the animation.
 *
 * @param enableAnimation Boolean flag to enable or disable the pie chart animation.
 * @param animationDuration Duration of the animation in milliseconds, determining how long the animation takes to complete.
 * @param animationRotations Number of rotations the pie chart performs during the animation.
 */
@Keep
@Stable
data class PieChartAnimationConfig(
    val enableAnimation: Boolean,
    val animationDuration: Int,
    val animationRotations: Int,
)

object PieChartDefaults {

    /**
     * A default [TextStyle] used for text elements within the Pie Chart, such as item labels.
     */
    private val textStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
    )

    /**
     * A default list of colors used for the [PieChart] slices when no custom colors are provided.
     * The colors in this list are designed to be visually appealing and distinct, ensuring
     * that each slice of the pie chart is easily distinguishable.
     */
    private val defaultColorList = listOf(
        Color(0xFF519DE9),
        Color(0xFF007BFF),
        Color(0xFF03DAC5),
        Color(0xFF625CEE),
        Color(0xFFF7B7A3),
        Color(0xFFEA5F89),
        Color(0xFF9B3192),
        Color(0xFF2954A1),
    )

    /**
     * Factory method to create a [PieChartConfig] instance with default or custom settings.
     * This configuration controls the appearance of the pie chart, including its size, thickness,
     * colors, and text styling.
     *
     * @param radius The radius of the pie chart, defining its overall size.
     * @param thickness The thickness of the pie chart slices, determining how wide each slice appears.
     * @param colorsList A list of colors for the slices. If not provided, a default color list is used.
     * @param enableChartItems Boolean flag to enable or disable the display of chart items like legends or labels.
     * @param isChartItemScrollEnable Boolean flag to enable or disable scrolling of chart items when there are too many to fit within the available space.
     * @param textStyle The [TextStyle] applied to slice labels. If not provided, a default text style is used.
     * @return A configured [PieChartConfig] instance.
     */
    fun pieChartConfig(
        radius: Dp = 75.dp,
        thickness: Dp = 25.dp,
        colorsList: List<Color> = defaultColorList,
        enableChartItems: Boolean = true,
        isChartItemScrollEnable: Boolean = false,
        textStyle: TextStyle = PieChartDefaults.textStyle
    ): PieChartConfig {

        return PieChartConfig(
            radius = radius,
            thickness = thickness,
            colorsList = colorsList,
            enableChartItems = enableChartItems,
            isChartItemScrollEnable = isChartItemScrollEnable,
            textStyle = textStyle
        )

    }

    /**
     * Factory method to create a [PieChartAnimationConfig] instance with default or custom settings.
     * This configuration controls the animation behavior of the pie chart, including the animation
     * duration and the number of rotations.
     *
     * @param enableAnimation Boolean flag to enable or disable the pie chart animation.
     * @param animationDuration The duration of the animation in milliseconds.
     * @param animationRotations The number of rotations the pie chart performs during the animation.
     * @return A configured [PieChartAnimationConfig] instance.
     */
    fun pieChartAnimationConfig(
        enableAnimation: Boolean = true,
        animationDuration: Int = 1000,
        animationRotations: Int = 11,
    ): PieChartAnimationConfig {
        return PieChartAnimationConfig(
            enableAnimation = enableAnimation,
            animationRotations = animationRotations,
            animationDuration = animationDuration
        )
    }

}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    chartData: Map<String, Float>,
    pieChartConfig: PieChartConfig = PieChartDefaults.pieChartConfig(),
    pieChartAnimationConfig: PieChartAnimationConfig = PieChartDefaults.pieChartAnimationConfig(),
    chartItemModifier: Modifier = Modifier,
    /** This compose function returns list of [PieChartEntry] - [text, value, and the color] of each item*/
    chartItems: @Composable ((List<PieChartEntry>) -> Unit)? = null,
    onItemClick: ((PieChartEntry) -> Unit)? = null
) {

    val totalSum = chartData.values.sum()
    val floatValue = mutableListOf<Float>()

    /* To set the value of each Arc according to
    the value given in the data, we have used a simple formula.
    Calculate the sweep angle for each slice of the pie chart based on its proportion
    relative to the total sum of all values. The `floatValue` list stores these angles
    in degrees (0-360), where each angle represents the portion of the pie chart
    corresponding to the value at the given index in the `data` map. */
    chartData.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values / totalSum)
    }

    var animationPlayed by rememberSaveable { mutableStateOf(!pieChartAnimationConfig.enableAnimation) }

    var lastValue = 0f

    // Calculating the animation size based on the radius of the pie chart
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) pieChartConfig.radius.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = pieChartAnimationConfig.animationDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = "animateFloatAsState"
    )

    // Calculating the rotation of the pie chart during animation
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * pieChartAnimationConfig.animationRotations else 0f,
        animationSpec = tween(
            durationMillis = pieChartAnimationConfig.animationDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = "animateFloatAsState"
    )

    val list: List<PieChartEntry> = chartData.mapToPieChartEntries(
        colors = pieChartConfig.colorsList
    )

    /* The vertical padding is calculated using a formula to ensure that the pie chart's arcs
    do not get cut off when rotated. As the pie chart is drawn using multiple arcs, rotation
    may cause parts of the arcs to extend beyond the designated area, leading to visual clipping
    at the edges (top, bottom, right, and left). This formula adjusts the padding dynamically
    based on the radius of the chart to provide enough space around the pie chart, preventing
    any part of the arcs from being cut off during rotation. */
    val pieChartPadding = (pieChartConfig.radius * 2) / (pieChartConfig.radius / 8.dp)

    val scrollState = rememberScrollState()

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Drawing the Pie Chart using Canvas Arc
        Box(

            modifier = Modifier
                .padding(all = pieChartPadding)
                .size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(pieChartConfig.radius * 2f)
                    .rotate(animateRotation)
            ) {
                // Draw each Arc for each data entry in the Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = list[index].color,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(pieChartConfig.thickness.toPx(), cap = StrokeCap.Butt),
                    )
                    lastValue += value
                }
            }
        }

        // Displaying additional chart items like labels, if enabled
        if (pieChartConfig.enableChartItems) {
            if (chartItems !== null) {
                chartItems(list)
            } else {
                PieChartItems(
                    modifier = chartItemModifier.padding(top = pieChartPadding),
                    data = list,
                    userScrollEnable = pieChartConfig.isChartItemScrollEnable,
                    scrollState = scrollState,
                    textStyle = pieChartConfig.textStyle,
                    onItemClick = onItemClick
                )
            }
        }

    }

}

/**
 * PieChartItems is a composable function that displays a list of chart items, such as labels,
 * for the PieChart. Each item shows the name, value, and color of the corresponding pie slice.
 *
 * @param modifier Modifier to be applied to the container of the chart items.
 * @param data List of [PieChartEntry] representing the pie chart slices.
 * @param userScrollEnable Boolean flag to enable or disable horizontal scrolling of chart items.
 * @param scrollState ScrollState to manage the scroll position of the chart items.
 * @param textStyle [TextStyle] applied to the text elements in the chart items.
 * @param onItemClick Lambda function that is triggered when a chart item is clicked, passing the clicked [PieChartEntry] as a parameter.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PieChartItems(
    modifier: Modifier,
    data: List<PieChartEntry>,
    userScrollEnable: Boolean,
    scrollState: ScrollState,
    textStyle: TextStyle,
    onItemClick: ((PieChartEntry) -> Unit)?,
) {

    // Apply horizontal scroll only if user scrolling is enabled
    FlowRow(
        modifier = modifier.optionalHorizontalScroll(enabled = userScrollEnable, state = scrollState),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        data.forEach { item ->

            val decimalFormat = DecimalFormat("##.##").format(item.value)

            SuggestionChip(
                onClick = {
                    if (onItemClick != null) {
                        onItemClick(item)
                    }
                },
                label = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp)
                            .background(item.color)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${item.name} : $decimalFormat", style = textStyle)
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = item.color.copy(alpha = 0.2f)
                ),
                border = null,
                shape = CircleShape
            )
        }
    }
}

/**
 * Extension function that conditionally applies horizontal scrolling to a Modifier.
 *
 * @param enabled Boolean flag indicating whether horizontal scrolling should be applied.
 * @param state ScrollState to manage the scroll position.
 * @return The modified Modifier with or without horizontal scrolling.
 */
private fun Modifier.optionalHorizontalScroll(enabled: Boolean, state: ScrollState): Modifier {
    return if (enabled) {
        this.horizontalScroll(state)
    } else {
        this
    }
}

data class PieChartEntry(
    val name: String,
    val value: Float,
    val color: Color
)

/**
 * Extension function that maps a [Map] of chart data to a list of [PieChartEntry] objects.
 * Each entry in the map is transformed into a [PieChartEntry] with a corresponding color.
 *
 * @param colors A list of colors to be used for the pie chart slices. The colors are assigned
 *               to the entries in order. If there are more entries than colors, the colors
 *               will be reused with a reduced alpha value.
 *
 * @return A list of [PieChartEntry] objects, each representing a slice in the pie chart with
 *         a name, value, and color.
 */
fun Map<String, Float>.mapToPieChartEntries(
    colors: List<Color>
): List<PieChartEntry> {

    return this.entries.mapIndexed { index, entry ->
        PieChartEntry(
            name = entry.key,
            value = entry.value,
            color = colors.getOrElse(index) { colors[index % colors.size].copy(alpha = 0.5f) }
        )
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    val chartData = mapOf(
        Pair("Test-1", 50f),
        Pair("Test-2", 80f),
        Pair("Test-3.beta", 30f),
        Pair("Test-4", 90f),
        Pair("Test-5", 45f),
        Pair("Test-6.beta", 30f),
        Pair("Test-7", 90f),
        Pair("Test-8", 45f),
        Pair("Test-9", 50f),
        Pair("Test-10", 80f),
        Pair("Test-11", 50f),
        Pair("Test-12", 80f),
    )

    PieChart(
        modifier = Modifier.fillMaxWidth(),
        chartData = chartData,
        pieChartConfig = PieChartDefaults.pieChartConfig(
            radius = 90.dp,
            isChartItemScrollEnable = false
        ),
        pieChartAnimationConfig = PieChartDefaults.pieChartAnimationConfig(
            animationDuration = 2000
        )
    )
}