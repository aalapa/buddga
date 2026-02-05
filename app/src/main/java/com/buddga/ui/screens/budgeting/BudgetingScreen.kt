package com.buddga.ui.screens.budgeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.BudgetWithCategory
import com.buddga.ui.theme.BudgetOverspent
import com.buddga.ui.theme.BudgetPositive
import com.buddga.ui.theme.BudgetUnderfunded
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import com.buddga.ui.theme.WarningOrange
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetingScreen(
    onNavigateToAddBudget: () -> Unit,
    onNavigateToAddCategory: () -> Unit,
    viewModel: BudgetingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy") }
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.US) }
    val collapsedGroups = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { viewModel.previousMonth() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Previous month",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Text(
                            text = uiState.currentMonth.format(monthFormatter),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        IconButton(onClick = { viewModel.nextMonth() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Next month",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
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
                onClick = onNavigateToAddBudget,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Budget"
                )
            }
        }
    ) { paddingValues ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
            return@Scaffold
        }

        // Group budgets by category groupName
        val groupedBudgets: Map<String, List<BudgetWithCategory>> = remember(uiState.budgets) {
            uiState.budgets.groupBy { it.category.groupName }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // -- To Be Budgeted Banner --
            item(key = "toBeBudgeted") {
                ToBeBudgetedBanner(
                    amount = uiState.toBeBudgeted,
                    currencyFormatter = currencyFormatter
                )
            }

            // -- Budget Summary Row --
            item(key = "summaryRow") {
                BudgetSummaryRow(
                    totalBudgeted = uiState.totalBudgeted,
                    totalSpent = uiState.totalSpent,
                    currencyFormatter = currencyFormatter
                )
            }

            // -- Empty State --
            if (uiState.budgets.isEmpty()) {
                item(key = "emptyState") {
                    EmptyBudgetState(
                        onAddBudget = onNavigateToAddBudget
                    )
                }
            } else {
                // -- Category Groups --
                groupedBudgets.forEach { (groupName, budgetsInGroup) ->
                    val isCollapsed = collapsedGroups[groupName] ?: false

                    // Group totals
                    val groupBudgeted = budgetsInGroup.sumOf { it.budget.allocated }
                    val groupActivity = budgetsInGroup.sumOf { it.budget.spent }
                    val groupAvailable = budgetsInGroup.sumOf { it.budget.available }

                    // Group Header
                    item(key = "group_header_$groupName") {
                        CategoryGroupHeader(
                            groupName = groupName,
                            budgeted = groupBudgeted,
                            activity = groupActivity,
                            available = groupAvailable,
                            isCollapsed = isCollapsed,
                            currencyFormatter = currencyFormatter,
                            onToggle = {
                                collapsedGroups[groupName] = !isCollapsed
                            }
                        )
                    }

                    // Category Rows (animated collapse)
                    item(key = "group_content_$groupName") {
                        AnimatedVisibility(
                            visible = !isCollapsed,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column {
                                budgetsInGroup.forEachIndexed { index, budgetWithCategory ->
                                    BudgetCategoryRow(
                                        budgetWithCategory = budgetWithCategory,
                                        currencyFormatter = currencyFormatter
                                    )
                                    if (index < budgetsInGroup.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(start = 48.dp),
                                            thickness = 0.5.dp,
                                            color = MaterialTheme.colorScheme.outlineVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom spacing for FAB
            item(key = "bottomSpacer") {
                Spacer(modifier = Modifier.height(88.dp))
            }
        }
    }
}

// ──────────────────────────────────────────────
// To Be Budgeted Banner
// ──────────────────────────────────────────────

@Composable
private fun ToBeBudgetedBanner(
    amount: BigDecimal,
    currencyFormatter: NumberFormat
) {
    val backgroundColor = when {
        amount > BigDecimal.ZERO -> IncomeGreen
        amount < BigDecimal.ZERO -> ExpenseRed
        else -> WarningOrange
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currencyFormatter.format(amount),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "To Be Budgeted",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

// ──────────────────────────────────────────────
// Budget Summary Row
// ──────────────────────────────────────────────

@Composable
private fun BudgetSummaryRow(
    totalBudgeted: BigDecimal,
    totalSpent: BigDecimal,
    currencyFormatter: NumberFormat
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryItem(
            label = "Budgeted",
            amount = currencyFormatter.format(totalBudgeted),
            color = MaterialTheme.colorScheme.onSurface
        )
        SummaryItem(
            label = "Activity",
            amount = currencyFormatter.format(totalSpent.negate()),
            color = ExpenseRed
        )
        SummaryItem(
            label = "Available",
            amount = currencyFormatter.format(totalBudgeted - totalSpent),
            color = if (totalBudgeted >= totalSpent) BudgetPositive else BudgetOverspent
        )
    }
}

@Composable
private fun SummaryItem(
    label: String,
    amount: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = amount,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ──────────────────────────────────────────────
// Category Group Header
// ──────────────────────────────────────────────

@Composable
private fun CategoryGroupHeader(
    groupName: String,
    budgeted: BigDecimal,
    activity: BigDecimal,
    available: BigDecimal,
    isCollapsed: Boolean,
    currencyFormatter: NumberFormat,
    onToggle: () -> Unit
) {
    val availableColor = when {
        available > BigDecimal.ZERO -> BudgetPositive
        available < BigDecimal.ZERO -> BudgetOverspent
        else -> BudgetUnderfunded
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isCollapsed) {
                Icons.Filled.KeyboardArrowDown
            } else {
                Icons.Filled.KeyboardArrowUp
            },
            contentDescription = if (isCollapsed) "Expand $groupName" else "Collapse $groupName",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = groupName.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        // Group column totals
        Text(
            text = currencyFormatter.format(budgeted),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = currencyFormatter.format(activity.negate()),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = currencyFormatter.format(available),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = availableColor,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )
    }
}

// ──────────────────────────────────────────────
// Budget Category Row
// ──────────────────────────────────────────────

@Composable
private fun BudgetCategoryRow(
    budgetWithCategory: BudgetWithCategory,
    currencyFormatter: NumberFormat
) {
    val budget = budgetWithCategory.budget
    val category = budgetWithCategory.category
    val available = budget.available

    val availableColor = when {
        available > BigDecimal.ZERO -> BudgetPositive
        available < BigDecimal.ZERO -> BudgetOverspent
        else -> BudgetUnderfunded
    }

    val availableBackgroundColor = when {
        available > BigDecimal.ZERO -> BudgetPositive.copy(alpha = 0.12f)
        available < BigDecimal.ZERO -> BudgetOverspent.copy(alpha = 0.12f)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category color dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color(category.color))
        )
        Spacer(modifier = Modifier.width(12.dp))

        // Category name
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Budgeted column
        Text(
            text = currencyFormatter.format(budget.allocated),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )

        // Activity column (spent, shown as negative)
        Text(
            text = if (budget.spent > BigDecimal.ZERO) {
                currencyFormatter.format(budget.spent.negate())
            } else {
                currencyFormatter.format(BigDecimal.ZERO)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = if (budget.spent > BigDecimal.ZERO) ExpenseRed else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )

        // Available column with colored pill background
        Box(
            modifier = Modifier
                .width(80.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(availableBackgroundColor)
                .padding(horizontal = 6.dp, vertical = 2.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = currencyFormatter.format(available),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = availableColor,
                textAlign = TextAlign.End
            )
        }
    }
}

// ──────────────────────────────────────────────
// Empty State
// ──────────────────────────────────────────────

@Composable
private fun EmptyBudgetState(
    onAddBudget: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No budgets set for this month",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Start by creating your first budget.\nGive every dollar a job and take control of your spending.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.clickable(onClick = onAddBudget),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Create First Budget",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
