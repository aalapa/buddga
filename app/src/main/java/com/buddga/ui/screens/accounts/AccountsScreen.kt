package com.buddga.ui.screens.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.Account
import com.buddga.domain.model.AccountType
import com.buddga.ui.components.AccountCard
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateToAddAccount: () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.US) }
    
    // Track expanded/collapsed state for each category (default: collapsed)
    val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }

    // Group accounts by type
    val checkingAccounts = uiState.accounts.filter { it.type == AccountType.CHECKING }
    val savingsAccounts = uiState.accounts.filter { it.type == AccountType.SAVINGS }
    val creditAccounts = uiState.accounts.filter { it.type == AccountType.CREDIT_CARD }
    val otherAccounts = uiState.accounts.filter {
        it.type != AccountType.CHECKING &&
                it.type != AccountType.SAVINGS &&
                it.type != AccountType.CREDIT_CARD
    }

    Scaffold(
        topBar = {
            // Compact header with total balance
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Accounts",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = currencyFormat.format(uiState.totalBalance),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${uiState.accountCount} accounts",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddAccount,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Account",
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

            // Checking Accounts
            if (checkingAccounts.isNotEmpty()) {
                item {
                    val isExpanded = expandedCategories.getOrDefault("Checking", false)
                    CollapsibleAccountGroupHeader(
                        title = "Checking",
                        accounts = checkingAccounts,
                        isExpanded = isExpanded,
                        currencyFormat = currencyFormat,
                        onToggle = { 
                            expandedCategories["Checking"] = !isExpanded
                        }
                    )
                }
                if (checkingAccounts.size == 1 || expandedCategories.getOrDefault("Checking", false)) {
                    items(checkingAccounts) { account ->
                        AccountCard(
                            account = account,
                            onClick = { /* TODO: Navigate to account details */ }
                        )
                    }
                }
            }

            // Savings Accounts
            if (savingsAccounts.isNotEmpty()) {
                item {
                    val isExpanded = expandedCategories.getOrDefault("Savings", false)
                    CollapsibleAccountGroupHeader(
                        title = "Savings",
                        accounts = savingsAccounts,
                        isExpanded = isExpanded,
                        currencyFormat = currencyFormat,
                        onToggle = { 
                            expandedCategories["Savings"] = !isExpanded
                        }
                    )
                }
                if (savingsAccounts.size == 1 || expandedCategories.getOrDefault("Savings", false)) {
                    items(savingsAccounts) { account ->
                        AccountCard(
                            account = account,
                            onClick = { /* TODO: Navigate to account details */ }
                        )
                    }
                }
            }

            // Credit Cards
            if (creditAccounts.isNotEmpty()) {
                item {
                    val isExpanded = expandedCategories.getOrDefault("Credit Cards", false)
                    CollapsibleAccountGroupHeader(
                        title = "Credit Cards",
                        accounts = creditAccounts,
                        isExpanded = isExpanded,
                        currencyFormat = currencyFormat,
                        onToggle = { 
                            expandedCategories["Credit Cards"] = !isExpanded
                        }
                    )
                }
                if (creditAccounts.size == 1 || expandedCategories.getOrDefault("Credit Cards", false)) {
                    items(creditAccounts) { account ->
                        AccountCard(
                            account = account,
                            onClick = { /* TODO: Navigate to account details */ }
                        )
                    }
                }
            }

            // Other Accounts
            if (otherAccounts.isNotEmpty()) {
                item {
                    val isExpanded = expandedCategories.getOrDefault("Other", false)
                    CollapsibleAccountGroupHeader(
                        title = "Other",
                        accounts = otherAccounts,
                        isExpanded = isExpanded,
                        currencyFormat = currencyFormat,
                        onToggle = { 
                            expandedCategories["Other"] = !isExpanded
                        }
                    )
                }
                if (otherAccounts.size == 1 || expandedCategories.getOrDefault("Other", false)) {
                    items(otherAccounts) { account ->
                        AccountCard(
                            account = account,
                            onClick = { /* TODO: Navigate to account details */ }
                        )
                    }
                }
            }

            // Empty state
            if (uiState.accounts.isEmpty() && !uiState.isLoading) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No accounts yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add your first account to start tracking your finances",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
            }
        }
    }
}

@Composable
private fun CollapsibleAccountGroupHeader(
    title: String,
    accounts: List<Account>,
    isExpanded: Boolean,
    currencyFormat: NumberFormat,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasMultipleAccounts = accounts.size > 1
    val totalBalance = accounts.fold(BigDecimal.ZERO) { sum, account -> sum + account.balance }
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = hasMultipleAccounts) { onToggle() }
            .padding(top = 8.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (hasMultipleAccounts) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                )
            }
        }
        
        // Show total when collapsed and has multiple accounts
        if (hasMultipleAccounts && !isExpanded) {
            Text(
                text = currencyFormat.format(totalBalance),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
