package com.buddga.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BudgetCategoryItem(
    categoryName: String,
    categoryColor: Color,
    spent: BigDecimal,
    budgeted: BigDecimal,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val available = budgeted - spent
    val progress = if (budgeted > BigDecimal.ZERO) {
        (spent.toFloat() / budgeted.toFloat()).coerceIn(0f, 1f)
    } else 0f
    val isOverspent = available < BigDecimal.ZERO

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category color indicator
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(categoryColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Category name and progress
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (isOverspent) ExpenseRed else categoryColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Amount display
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${currencyFormat.format(spent)} / ${currencyFormat.format(budgeted)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ToBeBudgetedCard(
    amount: BigDecimal,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val isPositive = amount >= BigDecimal.ZERO

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isPositive) IncomeGreen.copy(alpha = 0.1f)
            else ExpenseRed.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "To Be Budgeted",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = currencyFormat.format(amount),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isPositive) IncomeGreen else ExpenseRed
            )
        }
    }
}

@Composable
fun CategorySpendingLegendItem(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
