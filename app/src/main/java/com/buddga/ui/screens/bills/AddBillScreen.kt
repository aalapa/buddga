package com.buddga.ui.screens.bills

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.buddga.domain.model.BillFrequency
import com.buddga.domain.model.Category
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddBillViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var billName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedFrequency by remember { mutableStateOf(BillFrequency.MONTHLY) }
    var dueDate by remember { mutableStateOf(LocalDate.now().plusDays(7)) }
    var isAutoPay by remember { mutableStateOf(false) }
    var reminderDays by remember { mutableStateOf("3") }
    var notes by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var frequencyExpanded by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    val frequencies = listOf(
        BillFrequency.ONCE to "One Time",
        BillFrequency.WEEKLY to "Weekly",
        BillFrequency.BI_WEEKLY to "Bi-Weekly",
        BillFrequency.MONTHLY to "Monthly",
        BillFrequency.QUARTERLY to "Quarterly",
        BillFrequency.YEARLY to "Yearly"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Bill",
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
            // Bill Name
            OutlinedTextField(
                value = billName,
                onValueChange = { billName = it },
                label = { Text("Bill Name") },
                placeholder = { Text("e.g., Rent, Netflix") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Amount
            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        amount = newValue
                    }
                },
                label = { Text("Amount") },
                placeholder = { Text("0.00") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    placeholder = { Text("Select category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = selectedCategory?.let {
                        {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(it.color))
                            )
                        }
                    }
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    uiState.categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(Color(category.color))
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(category.name)
                                }
                            },
                            onClick = {
                                selectedCategory = category
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // Frequency Dropdown
            ExposedDropdownMenuBox(
                expanded = frequencyExpanded,
                onExpandedChange = { frequencyExpanded = it }
            ) {
                OutlinedTextField(
                    value = frequencies.find { it.first == selectedFrequency }?.second ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Frequency") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = frequencyExpanded,
                    onDismissRequest = { frequencyExpanded = false }
                ) {
                    frequencies.forEach { (frequency, label) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                selectedFrequency = frequency
                                frequencyExpanded = false
                            }
                        )
                    }
                }
            }

            // Due Date
            OutlinedTextField(
                value = dueDate.format(dateFormatter),
                onValueChange = {},
                readOnly = true,
                label = { Text("Due Date") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Select date")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            )

            // Auto Pay Toggle
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
                            text = "Auto Pay",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "This bill is paid automatically",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isAutoPay,
                        onCheckedChange = { isAutoPay = it }
                    )
                }
            }

            // Reminder Days
            OutlinedTextField(
                value = reminderDays,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d{0,2}$"))) {
                        reminderDays = newValue
                    }
                },
                label = { Text("Remind me (days before)") },
                placeholder = { Text("3") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                placeholder = { Text("Add any additional notes...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Save Button
            Button(
                onClick = {
                    when {
                        billName.isBlank() -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please enter a bill name")
                            }
                        }
                        amount.isBlank() || amount.toBigDecimalOrNull() == null -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please enter a valid amount")
                            }
                        }
                        selectedCategory == null -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please select a category")
                            }
                        }
                        else -> {
                            viewModel.addBill(
                                name = billName.trim(),
                                amount = amount.toBigDecimal(),
                                categoryId = selectedCategory!!.id,
                                frequency = selectedFrequency,
                                dueDate = dueDate,
                                isAutoPay = isAutoPay,
                                reminderDays = reminderDays.toIntOrNull() ?: 3,
                                notes = notes.trim()
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
                    text = "Save Bill",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Date Picker Dialog
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = dueDate
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                dueDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
