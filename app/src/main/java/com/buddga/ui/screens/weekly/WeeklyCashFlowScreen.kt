package com.buddga.ui.screens.weekly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.ui.components.BillItem
import com.buddga.ui.components.ChartLegendRow
import com.buddga.ui.components.WeeklyBarChart
import com.buddga.ui.theme.CategoryIncome
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeeklyCashFlowScreen(
    onNavigateBack: () -> Unit
) {
    // Sample weekly data
    val weeklyData = listOf(
        "1" to (800f to 600f),
        "12" to (900f to 750f),
        "10" to (700f to 500f),
        "17" to (600f to 800f),
        "29" to (850f to 650f),
        "30M" to (700f to 900f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weekly Cash Flow",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Weekly Bar Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Weekly Overview",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        WeeklyBarChart(
                            data = weeklyData,
                            modifier = Modifier.fillMaxWidth()
                        )

                        ChartLegendRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }

            // Summary Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = IncomeGreen.copy(alpha = 0.1f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Total Income",
                                style = MaterialTheme.typography.labelMedium,
                                color = IncomeGreen
                            )
                            Text(
                                text = "$2,500",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = IncomeGreen
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = ExpenseRed.copy(alpha = 0.1f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Cost Not",
                                style = MaterialTheme.typography.labelMedium,
                                color = ExpenseRed
                            )
                            Text(
                                text = "$1,500",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = ExpenseRed
                            )
                        }
                    }
                }
            }

            // Summary Section
            item {
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SummaryRow(label = "Total", value = "$2,000", showCheckIcon = true)
                        Spacer(modifier = Modifier.height(8.dp))
                        SummaryRow(label = "Bills Witty", value = "$1,500", dueDate = "Due 10th 26th")
                    }
                }
            }

            // Upcoming Section
            item {
                Text(
                    text = "Upcoming",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                BillItem(
                    name = "Rent",
                    amount = BigDecimal("7500"),
                    dueDate = "In 205",
                    categoryColor = ExpenseRed
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    dueDate: String? = null,
    showCheckIcon: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            if (dueDate != null) {
                Text(
                    text = dueDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
