package com.buddga.ui.screens.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.AccountType
import com.buddga.ui.theme.AccountChecking
import com.buddga.ui.theme.AccountCredit
import com.buddga.ui.theme.AccountSavings
import com.buddga.ui.theme.ChartColors
import kotlinx.coroutines.launch
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddAccountScreen(
    onNavigateBack: () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    var accountName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(AccountType.CHECKING) }
    var balance by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(AccountChecking) }
    var isOnBudget by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val accountTypes = listOf(
        AccountType.CHECKING to ("Checking" to Icons.Default.AccountBalance),
        AccountType.SAVINGS to ("Savings" to Icons.Default.Savings),
        AccountType.CREDIT_CARD to ("Credit Card" to Icons.Default.CreditCard),
        AccountType.CASH to ("Cash" to Icons.Default.Wallet)
    )

    val colorOptions = listOf(
        AccountChecking,
        AccountSavings,
        AccountCredit,
        Color(0xFFFF9800),
        Color(0xFFE91E63),
        Color(0xFF00BCD4),
        Color(0xFF607D8B),
        Color(0xFF9C27B0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Account",
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
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Account Name
            OutlinedTextField(
                value = accountName,
                onValueChange = { accountName = it },
                label = { Text("Account Name") },
                placeholder = { Text("e.g., Main Checking") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Account Type Selection
            Text(
                text = "Account Type",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                accountTypes.forEach { (type, labelAndIcon) ->
                    val (label, icon) = labelAndIcon
                    AccountTypeChip(
                        label = label,
                        icon = icon,
                        isSelected = selectedType == type,
                        onClick = { selectedType = type }
                    )
                }
            }

            // Starting Balance
            OutlinedTextField(
                value = balance,
                onValueChange = { newValue ->
                    // Allow only valid decimal input
                    if (newValue.isEmpty() || newValue.matches(Regex("^-?\\d*\\.?\\d{0,2}$"))) {
                        balance = newValue
                    }
                },
                label = { Text("Starting Balance") },
                placeholder = { Text("0.00") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Color Selection
            Text(
                text = "Account Color",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                colorOptions.forEach { color ->
                    ColorOption(
                        color = color,
                        isSelected = selectedColor == color,
                        onClick = { selectedColor = color }
                    )
                }
            }

            // On Budget Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "On Budget",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Include this account in your budget calculations",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isOnBudget,
                        onCheckedChange = { isOnBudget = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Save Button
            Button(
                onClick = {
                    if (accountName.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter an account name")
                        }
                        return@Button
                    }

                    val balanceValue = balance.toBigDecimalOrNull() ?: BigDecimal.ZERO

                    viewModel.addAccount(
                        name = accountName.trim(),
                        type = selectedType,
                        balance = balanceValue,
                        color = selectedColor.value.toLong()
                    )

                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Save Account",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AccountTypeChip(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected)
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
