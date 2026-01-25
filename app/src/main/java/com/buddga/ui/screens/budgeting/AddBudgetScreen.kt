package com.buddga.ui.screens.budgeting

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.Category
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddBudgetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var allocatedAmount by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Budget",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Month Selector
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { selectedMonth = selectedMonth.minusMonths(1) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous month"
                        )
                    }
                    Text(
                        text = selectedMonth.format(monthFormatter),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(onClick = { selectedMonth = selectedMonth.plusMonths(1) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next month"
                        )
                    }
                }
            }

            // Budget Amount
            OutlinedTextField(
                value = allocatedAmount,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        allocatedAmount = newValue
                    }
                },
                label = { Text("Budget Amount") },
                placeholder = { Text("0.00") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Category Selection
            Text(
                text = "Select Category",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            // Category List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val groupedCategories = uiState.categories.groupBy { it.groupName }

                groupedCategories.forEach { (groupName, categories) ->
                    item {
                        Text(
                            text = groupName,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(categories) { category ->
                        CategorySelectionItem(
                            category = category,
                            isSelected = selectedCategory?.id == category.id,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }

            // Save Button
            Button(
                onClick = {
                    when {
                        selectedCategory == null -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please select a category")
                            }
                        }
                        allocatedAmount.isBlank() || allocatedAmount.toBigDecimalOrNull() == null -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please enter a valid amount")
                            }
                        }
                        else -> {
                            viewModel.addBudget(
                                categoryId = selectedCategory!!.id,
                                month = selectedMonth,
                                allocated = allocatedAmount.toBigDecimal()
                            )
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Save Budget",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun CategorySelectionItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(category.color).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color(category.color))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
