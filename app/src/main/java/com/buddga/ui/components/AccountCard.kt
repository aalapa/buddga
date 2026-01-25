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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Wallet
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.buddga.domain.model.Account
import com.buddga.domain.model.AccountType
import com.buddga.ui.theme.AccountChecking
import com.buddga.ui.theme.AccountCredit
import com.buddga.ui.theme.AccountSavings
import com.buddga.ui.theme.ExpenseRed
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AccountCard(
    account: Account,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val (icon, color) = getAccountIconAndColor(account.type)
    val isNegative = account.balance < BigDecimal.ZERO

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with colored background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = account.type.name,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Account name and type
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = account.type.toDisplayName(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Balance
            Text(
                text = currencyFormat.format(account.balance),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = when {
                    account.type == AccountType.CREDIT_CARD -> if (isNegative) ExpenseRed else color
                    isNegative -> ExpenseRed
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
fun AccountSummaryCard(
    title: String,
    balance: BigDecimal,
    accountCount: Int,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
            Text(
                text = currencyFormat.format(balance),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "$accountCount account${if (accountCount != 1) "s" else ""}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun AddAccountButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalance,
                contentDescription = "Add Account",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Account",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun getAccountIconAndColor(type: AccountType): Pair<ImageVector, Color> {
    return when (type) {
        AccountType.CHECKING -> Icons.Default.AccountBalance to AccountChecking
        AccountType.SAVINGS -> Icons.Default.Savings to AccountSavings
        AccountType.CREDIT_CARD -> Icons.Default.CreditCard to AccountCredit
        AccountType.CASH -> Icons.Default.Wallet to AccountChecking
        AccountType.INVESTMENT -> Icons.Default.Savings to AccountSavings
        AccountType.OTHER -> Icons.Default.AccountBalance to AccountChecking
    }
}

private fun AccountType.toDisplayName(): String = when (this) {
    AccountType.CHECKING -> "Checking"
    AccountType.SAVINGS -> "Savings"
    AccountType.CREDIT_CARD -> "Credit Card"
    AccountType.CASH -> "Cash"
    AccountType.INVESTMENT -> "Investment"
    AccountType.OTHER -> "Other"
}
