package com.buddga.ui.screens.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.AccountType
import com.buddga.ui.components.AccountCard
import com.buddga.ui.components.AccountSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateToAddAccount: () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
            // Compact header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Accounts",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
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

            // Total Balance Summary
            item {
                AccountSummaryCard(
                    title = "Total Balance",
                    balance = uiState.totalBalance,
                    accountCount = uiState.accountCount
                )
            }

            // Checking Accounts
            if (checkingAccounts.isNotEmpty()) {
                item {
                    AccountGroupHeader(title = "Checking")
                }
                items(checkingAccounts) { account ->
                    AccountCard(
                        account = account,
                        onClick = { /* TODO: Navigate to account details */ }
                    )
                }
            }

            // Savings Accounts
            if (savingsAccounts.isNotEmpty()) {
                item {
                    AccountGroupHeader(title = "Savings")
                }
                items(savingsAccounts) { account ->
                    AccountCard(
                        account = account,
                        onClick = { /* TODO: Navigate to account details */ }
                    )
                }
            }

            // Credit Cards
            if (creditAccounts.isNotEmpty()) {
                item {
                    AccountGroupHeader(title = "Credit Cards")
                }
                items(creditAccounts) { account ->
                    AccountCard(
                        account = account,
                        onClick = { /* TODO: Navigate to account details */ }
                    )
                }
            }

            // Other Accounts
            if (otherAccounts.isNotEmpty()) {
                item {
                    AccountGroupHeader(title = "Other")
                }
                items(otherAccounts) { account ->
                    AccountCard(
                        account = account,
                        onClick = { /* TODO: Navigate to account details */ }
                    )
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

            // Action Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onNavigateToAddAccount) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text("Add Account")
                    }
                    TextButton(onClick = { /* TODO: Manage accounts */ }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text("Manage Accounts")
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
private fun AccountGroupHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}
