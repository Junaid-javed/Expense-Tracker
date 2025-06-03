package com.example.expensetracker.features.expense

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.gray
import com.example.expensetracker.ui.theme.light_gray
import com.example.expensetracker.ui.theme.zinc
import com.example.expensetracker.utils.Utils
import com.example.expensetracker.utils.dottedBorder
import com.example.expensetracker.viewmodel.expense.AddExpenseViewModel
import com.example.expensetracker.viewmodel.expense.AddExpenseViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(navController: NavController) {

    val viewModel =
        AddExpenseViewModelFactory(context = LocalContext.current).create(AddExpenseViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                })

            Row(
                modifier = Modifier
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left), contentDescription = null,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Text(
                    text = "Add Expense", fontSize = 20.sp, color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                )
            }
            DataForm(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onAddExpense = {
                    coroutineScope.launch {
                        if (viewModel.addExpense(it)) {
                            navController.popBackStack()
                        }
                    }
                }

            )
        }
    }
}

@Composable
fun DataForm(modifier: Modifier, onAddExpense: (expense: ExpenseEntity) -> Unit) {

    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableLongStateOf(0L)
    }
    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    val category = remember {
        mutableStateOf("")
    }
    val type = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())


    ) {
        Text(
            text = "Name", fontSize = 14.sp, color = zinc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = name.value, onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Amount", fontSize = 14.sp, color = zinc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = amount.value, onValueChange = { amount.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Date", fontSize = 14.sp, color = zinc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = if (date.longValue != 0L) Utils.dateFormatter(date.longValue) else "",
            onValueChange = { date.longValue = it.toLong() },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange, contentDescription = null,
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Category", fontSize = 14.sp, color = zinc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CategorySelection(list = listOf("PayPal", "UpWork", "Travel", "Salary"), onItemSelected = {
            category.value = it
        })

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Type", fontSize = 14.sp, color = zinc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CategorySelection(list = listOf("Income", "Expense"), onItemSelected = {
            type.value = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .dottedBorder(color = light_gray, strokeWidth = 1.dp, 8.dp, 20f)
                .clickable {
                    val expenseEntity = ExpenseEntity(
                        title = name.value,
                        amount = amount.value.toDoubleOrNull() ?: 0.0,
                        date = date.longValue.toString(),
                        category = category.value,
                        type = type.value,
                    )
                    onAddExpense(expenseEntity)
                },
            Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(R.drawable.plus_circle), contentDescription = "Add")
                Text(
                    text = "Add Expense",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.labelMedium,
                    color = gray,
                )
            }
        }

    }
    if (dateDialogVisibility.value) {
        DatePickDialog(onSelectedDate = {
            date.longValue = it
            dateDialogVisibility.value = false
        }, onDismiss = {
            dateDialogVisibility.value = false
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickDialog(
    onSelectedDate: (date: Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onSelectedDate(selectedDate) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelection(list: List<String>, onItemSelected: (item: String) -> Unit) {
    val expended = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf(list[0])
    }
    ExposedDropdownMenuBox(expanded = expended.value, onExpandedChange = { expended.value = it }) {
        OutlinedTextField(
            value = selectedItem.value,
            onValueChange = { selectedItem.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended.value)
            })
        ExposedDropdownMenu(
            expanded = expended.value,
            onDismissRequest = { expended.value = false }) {
            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it, fontWeight = FontWeight.Normal) },
                    onClick = {
                        expended.value = false
                        selectedItem.value = it
                        onItemSelected(it)
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExpensePreview() {
    AddExpenseScreen(rememberNavController())
}