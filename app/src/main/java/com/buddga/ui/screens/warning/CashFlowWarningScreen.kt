package com.buddga.ui.screens.warning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.ui.components.BillItem
import com.buddga.ui.components.IncomeItem
import com.buddga.ui.components.LowBalanceIndicator
import com.buddga.ui.components.WarningBanner
import com.buddga.ui.theme.CategoryIncome
import com.buddga.ui.theme.ExpenseRed
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashFlowWarningScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cash Flow Warning",
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

            // Warning Banner
            item {
                WarningBanner(
                    title = "Low Cash Forecast",
                    message = "Expected cash balance is at risk of running out.",
                    isError = true
                )
            }

            // Low Balance Indicator
            item {
                LowBalanceIndicator(
                    currentBalance = BigDecimal("75"),
                    threshold = BigDecimal("500")
                )
            }

            // Timeline of Bills
            item {
                Text(
                    text = "Timeline of Bills",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                TimelineDropdown(
                    label = "Look Hargest 2nv",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Bills Section
            item {
                Text(
                    text = "Bills",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                BillItem(
                    name = "Rent",
                    amount = BigDecimal("1200"),
                    dueDate = "In 20th",
                    categoryColor = ExpenseRed
                )
            }

            item {
                IncomeItem(
                    name = "Paycheck",
                    amount = BigDecimal("2500"),
                    dueDate = "All 25th",
                    categoryColor = CategoryIncome
                )
            }

            // Actions Section
            item {
                Text(
                    text = "Recommended Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                ActionCard(
                    title = "Reduce spending",
                    description = "Consider reducing discretionary spending until your next paycheck"
                )
            }

            item {
                ActionCard(
                    title = "Move funds",
                    description = "Consider transferring funds from savings to cover upcoming bills"
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TimelineDropdown(
    label: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Card(
        modifier = modifier,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Expand",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Card(
        modifier = modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
