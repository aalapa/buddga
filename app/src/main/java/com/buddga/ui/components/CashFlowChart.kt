package com.buddga.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.domain.model.MonthlyCashFlow
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CashFlowLineChart(
    monthlyData: List<MonthlyCashFlow>,
    modifier: Modifier = Modifier
) {
    val incomeColor = IncomeGreen
    val expenseColor = ExpenseRed

    if (monthlyData.isEmpty()) {
        EmptyChartPlaceholder(modifier)
        return
    }

    val maxValue = remember(monthlyData) {
        monthlyData.maxOfOrNull {
            maxOf(it.income.toFloat(), it.expenses.toFloat())
        } ?: 1f
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
    ) {
        if (monthlyData.size < 2) return@Canvas

        val width = size.width
        val height = size.height
        val stepX = width / (monthlyData.size - 1)

        // Draw income line
        val incomePath = Path()
        monthlyData.forEachIndexed { index, data ->
            val x = index * stepX
            val y = height - (data.income.toFloat() / maxValue * height)
            if (index == 0) {
                incomePath.moveTo(x, y)
            } else {
                incomePath.lineTo(x, y)
            }
        }
        drawPath(
            path = incomePath,
            color = incomeColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw expense line
        val expensePath = Path()
        monthlyData.forEachIndexed { index, data ->
            val x = index * stepX
            val y = height - (data.expenses.toFloat() / maxValue * height)
            if (index == 0) {
                expensePath.moveTo(x, y)
            } else {
                expensePath.lineTo(x, y)
            }
        }
        drawPath(
            path = expensePath,
            color = expenseColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw data points
        monthlyData.forEachIndexed { index, data ->
            val x = index * stepX
            val incomeY = height - (data.income.toFloat() / maxValue * height)
            val expenseY = height - (data.expenses.toFloat() / maxValue * height)

            drawCircle(
                color = incomeColor,
                radius = 4.dp.toPx(),
                center = Offset(x, incomeY)
            )
            drawCircle(
                color = expenseColor,
                radius = 4.dp.toPx(),
                center = Offset(x, expenseY)
            )
        }
    }
}

@Composable
fun CashFlowSummaryCards(
    totalIncome: BigDecimal,
    totalExpenses: BigDecimal,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            title = "Total Income",
            amount = currencyFormat.format(totalIncome),
            color = IncomeGreen,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            title = "Expenses",
            amount = currencyFormat.format(totalExpenses),
            color = ExpenseRed,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = amount,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun BalanceProjectionChart(
    projections: List<Pair<String, Float>>,
    safeZoneThreshold: Float,
    modifier: Modifier = Modifier
) {
    val positiveColor = IncomeGreen
    val warningColor = ExpenseRed.copy(alpha = 0.3f)

    if (projections.isEmpty()) {
        EmptyChartPlaceholder(modifier)
        return
    }

    val maxValue = remember(projections) {
        projections.maxOfOrNull { it.second } ?: 1f
    }
    val minValue = remember(projections) {
        minOf(projections.minOfOrNull { it.second } ?: 0f, 0f)
    }
    val range = maxValue - minValue

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height
        val stepX = if (projections.size > 1) width / (projections.size - 1) else width

        // Draw safe zone threshold line
        val thresholdY = height - ((safeZoneThreshold - minValue) / range * height)
        drawLine(
            color = warningColor,
            start = Offset(0f, thresholdY),
            end = Offset(width, thresholdY),
            strokeWidth = 2.dp.toPx()
        )

        // Draw balance line
        val path = Path()
        projections.forEachIndexed { index, (_, value) ->
            val x = index * stepX
            val y = height - ((value - minValue) / range * height)
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(
            path = path,
            color = positiveColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun WeeklyBarChart(
    data: List<Pair<String, Pair<Float, Float>>>, // Label, (Income, Expense)
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        EmptyChartPlaceholder(modifier)
        return
    }

    val maxValue = remember(data) {
        data.maxOfOrNull { maxOf(it.second.first, it.second.second) } ?: 1f
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height
        val barGroupWidth = width / data.size
        val barWidth = barGroupWidth * 0.35f
        val gap = barGroupWidth * 0.1f

        data.forEachIndexed { index, (_, values) ->
            val (income, expense) = values
            val groupX = index * barGroupWidth

            // Income bar
            val incomeHeight = (income / maxValue) * height
            drawRect(
                color = IncomeGreen,
                topLeft = Offset(groupX + gap, height - incomeHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, incomeHeight)
            )

            // Expense bar
            val expenseHeight = (expense / maxValue) * height
            drawRect(
                color = ExpenseRed,
                topLeft = Offset(groupX + gap + barWidth + gap / 2, height - expenseHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, expenseHeight)
            )
        }
    }
}

@Composable
private fun EmptyChartPlaceholder(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No data available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ChartLegendRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        CategorySpendingLegendItem(name = "Income", color = IncomeGreen)
        Spacer(modifier = Modifier.width(24.dp))
        CategorySpendingLegendItem(name = "Expenses", color = ExpenseRed)
    }
}
