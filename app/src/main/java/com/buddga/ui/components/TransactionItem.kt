package com.buddga.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.domain.model.TransactionType
import com.buddga.domain.model.TransactionWithDetails
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import com.buddga.ui.theme.PendingYellow
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TransactionItem(
    transaction: TransactionWithDetails,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val isIncome = transaction.transaction.type == TransactionType.INCOME

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category color indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(transaction.categoryColor).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(transaction.categoryColor))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Transaction details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.transaction.payee,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transaction.categoryName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = transaction.accountName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Amount and status
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (isIncome) "+" else "-"}${currencyFormat.format(transaction.transaction.amount)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isIncome) IncomeGreen else ExpenseRed
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (transaction.transaction.isReconciled)
                            Icons.Default.CheckCircle else Icons.Default.Schedule,
                        contentDescription = if (transaction.transaction.isReconciled)
                            "Reconciled" else "Pending",
                        modifier = Modifier.size(12.dp),
                        tint = if (transaction.transaction.isReconciled)
                            IncomeGreen else PendingYellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (transaction.transaction.isReconciled) "Reconciled" else "Pending",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (transaction.transaction.isReconciled)
                            IncomeGreen else PendingYellow
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionDateHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = date,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TransactionSummaryCard(
    income: String,
    expenses: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Income",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = income,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = IncomeGreen
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = expenses,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ExpenseRed
                )
            }
        }
    }
}
