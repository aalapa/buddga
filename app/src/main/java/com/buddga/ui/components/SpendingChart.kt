package com.buddga.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.domain.model.CategorySpending
import com.buddga.ui.theme.ChartColors
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

data class PieChartSlice(
    val value: Float,
    val color: Color,
    val label: String
)

@Composable
fun SpendingPieChart(
    categorySpending: List<CategorySpending>,
    totalSpent: BigDecimal,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    val slices = remember(categorySpending) {
        if (categorySpending.isEmpty() || totalSpent == BigDecimal.ZERO) {
            listOf(PieChartSlice(1f, Color.Gray.copy(alpha = 0.3f), "No data"))
        } else {
            categorySpending.mapIndexed { index, spending ->
                PieChartSlice(
                    value = spending.spent.toFloat(),
                    color = Color(spending.category.color),
                    label = spending.category.name
                )
            }
        }
    }

    val total = remember(slices) {
        slices.sumOf { it.value.toDouble() }.toFloat()
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.width * 0.15f
            val radius = (size.minDimension - strokeWidth) / 2
            val center = Offset(size.width / 2, size.height / 2)

            var startAngle = -90f

            slices.forEach { slice ->
                val sweepAngle = if (total > 0) (slice.value / total) * 360f else 360f
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += sweepAngle
            }
        }

        // Center text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Total Spent",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = currencyFormat.format(totalSpent),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SpendingDonutChart(
    spent: BigDecimal,
    budgeted: BigDecimal,
    color: Color,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val progress = remember(spent, budgeted) {
        if (budgeted > BigDecimal.ZERO) {
            (spent.toFloat() / budgeted.toFloat()).coerceIn(0f, 1f)
        } else 0f
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.width * 0.12f
            val radius = (size.minDimension - strokeWidth) / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Background arc
            drawArc(
                color = Color.Gray.copy(alpha = 0.2f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Center text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun ChartLegend(
    items: List<Pair<String, Color>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        items.forEach { (name, color) ->
            CategorySpendingLegendItem(
                name = name,
                color = color,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}
