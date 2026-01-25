package com.buddga.ui.screens.bills

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
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
import com.buddga.ui.theme.IncomeGreen
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingBillsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddBill: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Upcoming Bills & Income",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddBill,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Bill",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Bills List
            item {
                BillItem(
                    name = "Paycheck",
                    amount = BigDecimal("2500"),
                    dueDate = "Apr 26th",
                    categoryColor = CategoryIncome,
                    isPaid = false
                )
            }

            item {
                BillItem(
                    name = "Loan Payment",
                    amount = BigDecimal("300"),
                    dueDate = "Apr 26th",
                    categoryColor = ExpenseRed
                )
            }

            item {
                BillItem(
                    name = "Rent",
                    amount = BigDecimal("1200"),
                    dueDate = "Apr 25th",
                    categoryColor = Color(0xFFE91E63)
                )
            }

            item {
                IncomeItem(
                    name = "Freelance Payment",
                    amount = BigDecimal("600"),
                    dueDate = "Apr 26th",
                    categoryColor = CategoryIncome
                )
            }

            // Cash Balance Section
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
                                text = "Cash Balance",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Safe Zone",
                                style = MaterialTheme.typography.bodySmall,
                                color = IncomeGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        BalanceProjectionChart(
                            projections = listOf(
                                "1" to 3000f,
                                "5" to 2800f,
                                "10" to 2500f,
                                "15" to 2000f,
                                "20" to 1500f,
                                "25" to 1200f,
                                "30" to 2500f
                            ),
                            safeZoneThreshold = 500f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Summary
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
                                text = "Bills Upcoming",
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
