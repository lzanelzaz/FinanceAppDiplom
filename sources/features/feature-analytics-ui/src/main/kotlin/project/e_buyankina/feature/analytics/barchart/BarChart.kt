package project.e_buyankina.feature.analytics.barchart

import androidx.annotation.Keep
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import project.e_buyankina.common.ui.preview.DayNightPreviews
import java.text.DecimalFormat
import kotlin.math.abs

@Keep
@Stable
data class GroupBarChartItem(
    val name: String,
    val barValues: List<BarValue>
)


/**
 * Data class representing an individual bar within a group in a grouped bar chart.
 *
 * @property value The raw float value of the bar, representing the value of the bar.
 * @property floatValue The normalized value of the bar, expressed as a float between 0 and 1.
 *                      This value is computed as a fraction of the maximum value among all bars
 *                      in the group, facilitating proportional rendering of the bars.
 */
@Keep
@Stable
data class BarValue(
    val value: Float,
    val floatValue: Float
)

/**
 * Extension function to convert a [Map] of grouped bar data into a list of [GroupBarChartItem] objects.
 *
 * This function processes a map where each key represents a group label and the value is a list of integers,
 * each representing the height (or value) of a bar within that group. The function normalizes these heights
 * based on the provided maximum value, allowing for proportional rendering of the bars in each group.
 * The resulting list contains `GroupBarChartItem` instances, each with a name and a list of normalized bar values.
 *
 * @param maxValue The maximum value among all bars, used to compute the normalized `floatValue` for each bar.
 *                 This value determines the scale factor for normalizing the bar heights.
 * @return A list of [GroupBarChartItem] objects, where each item includes the group label and a list of
 *         `BarValue` objects representing the bars within the group. Each `BarValue` contains the bar's
 *         original value and its normalized float value.
 */
fun Map<String, List<Float>>.mapToGroupBarChartItems(maxValue: Float): List<GroupBarChartItem> =
    mapValues { (key, value) ->
        GroupBarChartItem(
            name = key,
            barValues = value.map {
                BarValue(
                    value = it,
                    floatValue = (it / maxValue)
                )
            }
        )
    }.values.toList()

@Composable
fun BarChartPopup(
    popUpConfig: PopUpConfig,
    text: String,
    onDismissRequest: () -> Unit
) {
    // Popup content configuration
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismissRequest() },
    ) {
        // Content of the popup
        Box(
            modifier = Modifier
                .background(
                    color = popUpConfig.background,
                    shape = popUpConfig.shape
                )
                .padding(8.dp)
        ) {
            Text(
                text = text,
                style = popUpConfig.textStyle
            )
        }
    }

}

@Composable
fun GridLine(
    modifier: Modifier = Modifier,
    gridLineStyle: GridLineStyle,
) {
    Canvas(modifier = modifier.padding(top = gridLineStyle.strokeWidth / 2)) {
        // Convert Dp to pixels
        val dashLengthPx = gridLineStyle.dashLength.toPx()
        val gapLengthPx = gridLineStyle.gapLength.toPx()
        val strokeWidthPx = gridLineStyle.strokeWidth.toPx()

        // Define the PathEffect to create dashes
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)

        // Draw the dashed line
        drawLine(
            color = gridLineStyle.color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = strokeWidthPx,
            pathEffect = pathEffect,
            cap = gridLineStyle.dashCap
        )
    }
}

@Composable
fun XAxisLabel(
    itemName: String,
    xAxisTextStyle: TextStyle,
    textRotateAngle: Float,
    enableTextRotate: Boolean,
    onLabelClick: () -> Unit
) {

    // Composable function to display the X-axis label with click functionality.
    val xAxisScaleTextUI: @Composable () -> Unit = {
        Text(
            text = itemName,
            style = xAxisTextStyle, // Applies the specified text style to the label
            modifier = Modifier.clickable {
                onLabelClick()
            }
        )
    }

    val textToLengthRatio = if (
        itemName.length <= 4
    ) 1.5f else if (itemName.length <= 8) 1.7f else 2f

    if (enableTextRotate) {
        // Box used to contain the label text with rotation applied if rotation is enabled
        Box(
            modifier = Modifier
                .size(
                    itemName.length.dp * textToLengthRatio
                )
                .rotate(textRotateAngle)
        ) {
            xAxisScaleTextUI()
        }
    } else {
        xAxisScaleTextUI() // Displays the label text without rotation
    }

}

@Composable
fun YAxisGridLines(
    gridLineStyle: GridLineStyle,
    yAxisStepHeight: Dp
) {
    Column(
        modifier = Modifier
            .padding(top = yAxisStepHeight)
            .fillMaxWidth(),
    ) {
        repeat(gridLineStyle.totalGridLines + 1) { index ->
            if (index != 0) {
                Row(
                    modifier = Modifier.height(height = yAxisStepHeight), // Sets the height of each row of Grid Line
                    verticalAlignment = Alignment.Top
                ) {
                    GridLine(
                        modifier = Modifier
                            .fillMaxWidth(),
                        gridLineStyle = gridLineStyle,
                    )
                }
            }
        }
    }
}

@Composable
fun YAxisScale(
    yAxisConfig: YAxisConfig,
    yAxisStepHeight: Dp,
    yAxisScaleStep: Float,
    barHeight: Dp,
) {
    Row {
        // Column for the Y-axis scale labels
        Column(horizontalAlignment = Alignment.End) {
            repeat(yAxisConfig.axisScaleCount + 1) { index ->
                val barScale = (yAxisConfig.axisScaleCount) - index
                Row(
                    modifier = Modifier.height(height = yAxisStepHeight),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = yAxisConfig.textPrefix + formatScaleValue(yAxisScaleStep * barScale) + yAxisConfig.textPostfix,
                        style = yAxisConfig.textStyle
                    )
                }
            }
        }


        // Y-axis line
        if (yAxisConfig.isAxisLineEnabled) {
            Spacer(modifier = Modifier.width(10.dp)) // Adds spacing between scale labels and Y-axis line

            Box(
                modifier = Modifier
                    .padding(top = yAxisStepHeight)
                    .clip(shape = yAxisConfig.axisLineShape)
                    .width(yAxisConfig.axisLineWidth)
                    .height(barHeight)
                    .background(yAxisConfig.axisLineColor)
            )
        }
    }
}

/**
 * Formats a numeric value for display on the Y-axis scale, using abbreviated notation for large values.
 *
 * This function converts large numeric values into a more readable format with suffixes:
 * - Values below 10,000 are displayed as-is.
 * - Values in the millions are formatted with an "M" suffix.
 * - Values in the thousands are formatted with a "K" suffix.
 *
 * @param value The numeric value to be formatted.
 * @return A formatted string representing the value with appropriate suffixes for readability.
 */
fun formatScaleValue(value: Float): String {

    // Defines the decimal format to display up to two decimal places
    val df = DecimalFormat("##.##")

    // For values less than 10,000, display the value as is
    if (value < 10000) {
        return df.format(value)
    }

    val am: Float
    // For values in millions, format the value with an "M" suffix
    if (abs(value / 1000000) >= 1) {
        am = value / 1000000
        return df.format(am) + "M"
    } else if (abs(value / 1000) >= 1) { // For values in thousands, format the value with a "K" suffix
        am = value / 1000
        return df.format(am) + "K"
    } else {
        // Default case, return the formatted value without suffix
        return df.format(value)
    }

}

@Keep
@Stable
data class GroupBarChartConfig(
    val colors: List<Color>,
    val height: Dp,
    val width: Dp,
    val shape: Shape,
    val gapBetweenBar: Dp,
    val gapBetweenGroup: Dp
)

@Keep
@Stable
data class XAxisConfig(
    val isAxisScaleEnabled: Boolean,
    val isAxisLineEnabled: Boolean,
    val axisLineWidth: Dp,
    val axisLineShape: Shape,
    val axisLineColor: Color,
    val textStyle: TextStyle,
)

/**
 * Configuration for the Y-axis in a bar chart.
 *
 * @property isAxisScaleEnabled Indicates whether the axis scale is enabled.
 * @property isAxisLineEnabled Indicates whether the axis line is enabled.
 * @property axisLineWidth The width of the axis line.
 * @property axisLineShape The shape of the axis line (e.g., rounded corners).
 * @property axisLineColor The color of the axis line.
 * @property axisScaleCount The number of scale divisions on the Y-axis.
 * @property textStyle The [TextStyle] of the text for axis labels.
 * @property textPrefix The prefix to be added before the scale value.
 * @property textPostfix The postfix to be added after the scale value.
 */
@Keep
@Stable
data class YAxisConfig(
    val isAxisScaleEnabled: Boolean,
    val isAxisLineEnabled: Boolean,
    val axisLineWidth: Dp,
    val axisLineShape: Shape,
    val axisLineColor: Color,
    val axisScaleCount: Int,
    val textStyle: TextStyle,
    val textPrefix: String,
    val textPostfix: String
)

/**
 * Configuration for grid lines in a bar chart.
 *
 * @property color The color of the grid lines.
 * @property strokeWidth The width of the grid lines.
 * @property dashLength The length of each dash in dashed grid lines.
 * @property gapLength The gap between dashes in dashed grid lines.
 * @property totalGridLines The total number of grid lines to be drawn in the chart.
 * @property dashCap The style of the stroke cap used for dashed lines.
 *
 */
@Keep
@Stable
data class GridLineStyle(
    val color: Color,
    val strokeWidth: Dp,
    val dashLength: Dp,
    val gapLength: Dp,
    val totalGridLines: Int,
    val dashCap: StrokeCap
)

/**
 * Configuration for pop-ups in a bar chart.
 *
 * @property enableXAxisPopUp Indicates whether pop-ups are enabled for the X-axis labels.
 * @property enableBarPopUp Indicates whether pop-ups are enabled for the bars.
 * @property background The background color of the pop-ups.
 * @property shape The shape of the pop-ups (e.g., rounded corners).
 * @property textStyle The [TextStyle] of the text in the pop-ups.
 */
@Keep
@Stable
data class PopUpConfig(
    val enableXAxisPopUp: Boolean,
    val enableBarPopUp: Boolean,
    val background: Color,
    val shape: Shape,
    val textStyle: TextStyle
)

object BarChartDefaults {

    /**
     * Default [TextStyle] used for axis labels in the bar chart.
     */
    private val textStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
    )

    /**
     * Default enter transition for animating the bars in the column bar chart.
     *
     * This transition expands the bars vertically when they
     * first appear on the screen. It provides a smooth and visually appealing
     * entrance for the bars in the chart.
     *
     * @see EnterTransition
     */
    val enterTransitionVertically = expandVertically(animationSpec = tween(durationMillis = 1000))

    /**
     * Provides default configuration for grouped bars in a bar chart.
     *
     * @param colors A list of colors for the bars in each group.
     * @param height The height of the bars.
     * @param width The width of the bars.
     * @param shape The shape of the bars (e.g., rounded corners).
     * @param gapBetweenBar The gap between individual bars within a group.
     * @param gapBetweenGroup The gap between different groups of bars.
     * @return A [GroupBarChartConfig] with default values.
     */
    fun groupBarChartConfig(
        colors: List<Color> = listOf(
            Color(0xFF0066cc),
            Color(0xFF8BC1F7),
            Color(0xFF519DE9),
            Color(0xFFB2B0EA)
        ),
        height: Dp = 200.dp,
        width: Dp = 20.dp,
        shape: Shape = RoundedCornerShape(
            6.dp
        ),
        gapBetweenBar: Dp = 0.dp,
        gapBetweenGroup: Dp = 20.dp
    ) = GroupBarChartConfig(
        colors = colors,
        height = height,
        width = width,
        shape = shape,
        gapBetweenBar = gapBetweenBar,
        gapBetweenGroup = gapBetweenGroup
    )

    /**
     * Provides default configuration for the X-axis in a bar chart.
     *
     * @param isAxisScaleEnabled Indicates whether the axis scale is enabled.
     * @param isAxisLineEnabled Indicates whether the axis line is enabled.
     * @param axisLineWidth The width of the axis line.
     * @param axisLineShape The shape of the axis line (e.g., rounded corners).
     * @param axisLineColor The color of the axis line.
     * @param textStyle The style of the text for axis labels.
     * @return An [XAxisConfig] with default values.
     */
    fun xAxisConfig(
        isAxisScaleEnabled: Boolean = true,
        isAxisLineEnabled: Boolean = true,
        axisLineWidth: Dp = 2.dp,
        axisLineShape: Shape = RoundedCornerShape(3.dp),
        axisLineColor: Color = Color.LightGray,
        textStyle: TextStyle = BarChartDefaults.textStyle,
    ) = XAxisConfig(
        isAxisScaleEnabled = isAxisScaleEnabled,
        isAxisLineEnabled = isAxisLineEnabled,
        axisLineWidth = axisLineWidth,
        axisLineShape = axisLineShape,
        axisLineColor = axisLineColor,
        textStyle = textStyle,
    )

    /**
     * Provides default configuration for the Y-axis in a bar chart.
     *
     * @param isAxisScaleEnabled Indicates whether the axis scale is enabled.
     * @param isAxisLineEnabled Indicates whether the axis line is enabled.
     * @param axisLineWidth The width of the axis line.
     * @param axisLineShape The shape of the axis line (e.g., rounded corners).
     * @param axisLineColor The color of the axis line.
     * @param axisScaleCount The number of scale divisions on the Y-axis.
     * @param textStyle The style of the text for axis labels.
     * @param textPrefix The prefix to be added before the scale value.
     * @param textPostfix The postfix to be added after the scale value.
     * @return A [YAxisConfig] with default values.
     */
    fun yAxisConfig(
        isAxisScaleEnabled: Boolean = true,
        isAxisLineEnabled: Boolean = true,
        axisLineWidth: Dp = 2.dp,
        axisLineShape: Shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp),
        axisLineColor: Color = Color.LightGray,
        axisScaleCount: Int = 4,
        textStyle: TextStyle = BarChartDefaults.textStyle,
        textPrefix: String = "",
        textPostfix: String = ""
    ) = YAxisConfig(
        isAxisScaleEnabled = isAxisScaleEnabled,
        isAxisLineEnabled = isAxisLineEnabled,
        axisLineWidth = axisLineWidth,
        axisLineShape = axisLineShape,
        axisLineColor = axisLineColor,
        axisScaleCount = axisScaleCount,
        textStyle = textStyle,
        textPrefix = textPrefix,
        textPostfix = textPostfix
    )

    /**
     * Provides default style for grid lines in the bar chart.
     *
     * @param color The color of the grid lines.
     * @param strokeWidth The width of the grid lines.
     * @param dashLength The length of each dash in dashed grid lines.
     * @param gapLength The gap between dashes in dashed grid lines.
     * @param totalGridLines The total number of grid lines to be drawn.
     * @param dashCap The style of the stroke cap used for dashed lines.
     * @return A [GridLineStyle] with default values.
     */
    fun gridLineStyle(
        color: Color = Color.LightGray,
        strokeWidth: Dp = 1.dp,
        dashLength: Dp = 8.dp,
        gapLength: Dp = 8.dp,
        totalGridLines: Int = 4,
        dashCap: StrokeCap = StrokeCap.Square
    ) = GridLineStyle(
        color = color,
        strokeWidth = strokeWidth,
        dashLength = dashLength,
        gapLength = gapLength,
        totalGridLines = totalGridLines,
        dashCap = dashCap
    )

    /**
     * Provides default configuration for pop-ups in the bar chart.
     *
     * @param enableXAxisPopUp Indicates whether pop-ups are enabled for the X-axis labels.
     * @param enableBarPopUp Indicates whether pop-ups are enabled for the bars.
     * @param background The background color of the pop-ups.
     * @param shape The shape of the pop-ups (e.g., rounded corners).
     * @param textStyle The style of the text in the pop-ups.
     * @return A [PopUpConfig] with default values.
     */
    fun popUpConfig(
        enableXAxisPopUp: Boolean = true,
        enableBarPopUp: Boolean = true,
        background: Color = Color(0xFFCCC2DC),
        shape: Shape = RoundedCornerShape(25),
        textStyle: TextStyle = BarChartDefaults.textStyle
    ) = PopUpConfig(
        enableXAxisPopUp = enableXAxisPopUp,
        enableBarPopUp = enableBarPopUp,
        background = background,
        shape = shape,
        textStyle = textStyle
    )
}

@Composable
fun GroupColumnBarChart(
    modifier: Modifier = Modifier,
    chartData: Map<String, List<Float>>,
    groupBarChartConfig: GroupBarChartConfig = BarChartDefaults.groupBarChartConfig(),
    yAxisConfig: YAxisConfig = BarChartDefaults.yAxisConfig(),
    xAxisConfig: XAxisConfig = BarChartDefaults.xAxisConfig(),
    popUpConfig: PopUpConfig = BarChartDefaults.popUpConfig(),
    gridLineStyle: GridLineStyle = BarChartDefaults.gridLineStyle(),
    maxBarValue: Float? = null,
    enableAnimation: Boolean = true,
    enterAnimation: EnterTransition = BarChartDefaults.enterTransitionVertically,
    exitAnimation: ExitTransition = shrinkVertically(),
    maxTextLengthXAxis: Int = 6,
    enableTextRotate: Boolean = false,
    textRotateAngle: Float = -60f,
    enableGridLines: Boolean = true,
    scrollEnable: Boolean = true,
    onBarClicked: ((Pair<String, Float>) -> Unit)? = null,
    onXAxisLabelClicked: ((Pair<String, List<Float>>) -> Unit)? = null
) {

    // Determine the maximum value in the data set, or use the provided max value
    val maxValue: Float = maxBarValue ?: chartData.values.flatten().maxOrNull() ?: 0f

    // Transform the data into a list of BarChartItem with normalized / float heights
    val barLists = chartData.mapToGroupBarChartItems(maxValue = maxValue)

    // State for managing whether the animation should be displayed
    var animationDisplay by remember {
        mutableStateOf(!enableAnimation)
    }

    // Trigger the animation when the composable is first launched
    if (enableAnimation) {
        LaunchedEffect(key1 = Unit) {
            animationDisplay = true
        }
    }

    // Calculate the Y-axis scale step based on the maximum value and the number of steps
    val yAxisScaleStep = maxValue / yAxisConfig.axisScaleCount
    val yAxisStepHeight = groupBarChartConfig.height / yAxisConfig.axisScaleCount

    Column(modifier = modifier) {

        Box(contentAlignment = Alignment.TopStart) {

            // Y-axis and bars container
            Row(horizontalArrangement = Arrangement.Start) {

                // Y-axis scale and line
                if (yAxisConfig.isAxisScaleEnabled) {
                    YAxisScale(
                        yAxisConfig = yAxisConfig,
                        yAxisStepHeight = yAxisStepHeight,
                        yAxisScaleStep = yAxisScaleStep,
                        barHeight = groupBarChartConfig.height
                    )
                }

                Box(modifier = Modifier.fillMaxWidth()) {

                    // grid lines
                    if (enableGridLines) {
                        YAxisGridLines(
                            gridLineStyle = gridLineStyle,
                            yAxisStepHeight = yAxisStepHeight
                        )
                    }

                    // Group Bars and X-axis labels container
                    LazyRow(
                        modifier = Modifier
                            .padding(top = yAxisStepHeight)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        userScrollEnabled = scrollEnable
                    ) {
                        items(barLists, key = { keyItem ->
                            keyItem.name
                        }) { barItem ->

                            // State for managing the popup
                            var isYAxisPopupVisible by remember { mutableStateOf(false) }
                            var yAxisPopupText by remember { mutableStateOf("") }

                            // Bar UI
                            Column(
                                modifier = Modifier
                                    .padding(start = groupBarChartConfig.gapBetweenGroup)
                                    .wrapContentSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                if (isYAxisPopupVisible && popUpConfig.enableXAxisPopUp) {
                                    BarChartPopup(
                                        popUpConfig = popUpConfig,
                                        text = yAxisPopupText,
                                        onDismissRequest = {
                                            isYAxisPopupVisible = false
                                        }
                                    )
                                }

                                // All bars of each group will be in this Row
                                Row(
                                    modifier = Modifier
                                        .wrapContentSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {

                                    barItem.barValues.forEachIndexed { index, barValue ->

                                        // State for managing the popup for each bar
                                        var isBarPopupVisible by remember { mutableStateOf(false) }
                                        var barPopupText by remember { mutableFloatStateOf(0f) }

                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = groupBarChartConfig.gapBetweenBar / 2)
                                                .height(groupBarChartConfig.height)
                                                .width(groupBarChartConfig.width),
                                            contentAlignment = Alignment.BottomCenter
                                        ) {

                                            if (isBarPopupVisible && popUpConfig.enableBarPopUp) {
                                                val decimalFormat = DecimalFormat("##.##")
                                                BarChartPopup(
                                                    popUpConfig = popUpConfig,
                                                    text = decimalFormat.format(barPopupText),
                                                    onDismissRequest = {
                                                        isBarPopupVisible = false
                                                    }
                                                )
                                            }

                                            // Lambda to create the bar with the specified shape, color, and click handling
                                            val barBox: @Composable () -> Unit = {

                                                Box(
                                                    modifier = Modifier
                                                        .clip(shape = groupBarChartConfig.shape)
                                                        .fillMaxHeight(barValue.floatValue)
                                                        .fillMaxWidth()
                                                        .background(
                                                            color = groupBarChartConfig.colors.getOrElse(
                                                                index = index,
                                                                defaultValue = { groupBarChartConfig.colors.last() }
                                                            )
                                                        )
                                                        .clickable {
                                                            if (popUpConfig.enableBarPopUp) {
                                                                barPopupText =
                                                                    barValue.value
                                                                isBarPopupVisible = true
                                                            }

                                                            if (onBarClicked != null) {
                                                                onBarClicked(
                                                                    Pair(
                                                                        barItem.name,
                                                                        barValue.value
                                                                    )
                                                                )
                                                            }
                                                        }
                                                )
                                            }

                                            // Animate the bar if animations are enabled
                                            if (enableAnimation) {
                                                androidx.compose.animation.AnimatedVisibility(
                                                    visible = animationDisplay,
                                                    enter = enterAnimation,
                                                    exit = exitAnimation
                                                ) {
                                                    barBox()
                                                }
                                            } else {
                                                barBox()
                                            }
                                        }
                                    }
                                }
                                // X-axis labels below each bar
                                if (xAxisConfig.isAxisScaleEnabled) {
                                    Spacer(modifier = Modifier.height(xAxisConfig.axisLineWidth + 5.dp))

                                    val itemName =
                                        if (barItem.name.length > maxTextLengthXAxis) "${
                                            barItem.name.take(maxTextLengthXAxis - 2)
                                        }..." else barItem.name

                                    XAxisLabel(
                                        itemName = itemName,
                                        xAxisTextStyle = xAxisConfig.textStyle,
                                        textRotateAngle = textRotateAngle,
                                        enableTextRotate = enableTextRotate,
                                        onLabelClick = {
                                            if (popUpConfig.enableXAxisPopUp) {
                                                yAxisPopupText = barItem.name
                                                isYAxisPopupVisible = true
                                            }

                                            if (onXAxisLabelClicked != null) {
                                                onXAxisLabelClicked(
                                                    Pair(
                                                        barItem.name,
                                                        barItem.barValues.map { it.value }
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // X-axis line at the bottom of the chart
            if (xAxisConfig.isAxisLineEnabled) {
                Box(
                    modifier = Modifier
                        .padding(top = groupBarChartConfig.height + yAxisStepHeight)
                        .fillMaxWidth()
                        .height(xAxisConfig.axisLineWidth)
                        .clip(shape = xAxisConfig.axisLineShape)
                        .background(xAxisConfig.axisLineColor)
                )

            }

        }
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    val groupData = mapOf(
        "Q1" to listOf(50f, 80f),
        "Q2" to listOf(70f, 40f),
        "Q3" to listOf(90f, 30f),
        "Q4" to listOf(60f, 90f)
    )

    GroupColumnBarChart(
        modifier = Modifier
            .fillMaxWidth(),
        // The data for the group column bar chart, Map<String, List<Float>>
        chartData = groupData,
        // The maximum value for the bars in the chart (used to normalize the bar heights, defining the y-axis scale).
        maxBarValue = 100f, // If null, it is determined from the chartData. Defaults to null.
        // Configuration for the group column bar chart, providing options for the bar appearance
        groupBarChartConfig = BarChartDefaults.groupBarChartConfig(
            width = 20.dp, // Set the width of each bar in the group
            shape = RoundedCornerShape(20),
            gapBetweenBar = 2.dp // Space between each bar in the group
        ),
        // Configuration for the grid lines in the background of the chart
        gridLineStyle = BarChartDefaults.gridLineStyle(
            color = Color(0xFFF7F1FF)
        ),
        // Enable or disable text rotation on the x-axis labels.
        enableTextRotate = false
        // Additional optional parameters to enhance the Column Bar Chart
        // Like: yAxisConfig, xAxisConfig, popUpConfig, animations, textRotateAngle, onBarClicked and more.
    )
}