package com.buddga.ui.screens.forecast

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.ui.components.BalanceProjectionChart
import com.buddga.ui.components.BillItem
import com.buddga.ui.components.IncomeItem
import com.buddga.ui.theme.CategoryIncome
import com.buddga.ui.theme.ExpenseRed
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashFlowForecastScreen(
    onNavigateBack: () -> Unit
) {
    // Sample data for demonstration
    val sampleProjections = listOf(
        "Week 1" to 5000f,
        "Week 2" to 4200f,
        "Week 3" to 3800f,
        "Week 4" to 3100f
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cash Flow Forecast",
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

            // Balance Projection Chart
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Balance Projection",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Next 30 days",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        BalanceProjectionChart(
                            projections = sampleProjections,
                            safeZoneThreshold = 1000f,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Low balance projection",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Safe Zone",
                                style = MaterialTheme.typography.bodySmall,
                                color = ExpenseRed.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Cash Balance Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Cash Balance",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = "$1,050",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Projected",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = "-$1,050",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = ExpenseRed
                            )
                        }
                    }
                }
            }

            // Upcoming Bills Section
            item {
                Text(
                    text = "Upcoming Bills",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                BillItem(
                    name = "Rent",
                    amount = BigDecimal("1200"),
                    dueDate = "Jan 25th",
                    categoryColor = ExpenseRed
                )
            }

            item {
                BillItem(
                    name = "Electricity",
                    amount = BigDecimal("100"),
                    dueDate = "Jan 25th",
                    categoryColor = Color(0xFF607D8B)
                )
            }

            // Upcoming Income Section
            item {
                Text(
                    text = "Upcoming Income",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                IncomeItem(
                    name = "Paycheck",
                    amount = BigDecimal("2500"),
                    dueDate = "Jan 25th",
                    categoryColor = CategoryIncome
                )
            }

            item {
                IncomeItem(
                    name = "Freelance Payment",
                    amount = BigDecimal("600"),
                    dueDate = "Jan 26th",
                    categoryColor = CategoryIncome
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
