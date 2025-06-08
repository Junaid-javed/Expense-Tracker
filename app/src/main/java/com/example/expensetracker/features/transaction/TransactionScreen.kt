package com.example.expensetracker.features.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.features.home.TransactionList
import com.example.expensetracker.ui.theme.zinc
import com.example.expensetracker.viewmodel.home.HomeViewModel
import com.example.expensetracker.viewmodel.home.HomeViewModelFactory

@Composable
fun TransactionScreen(navController: NavController) {

    val viewModel =
        HomeViewModelFactory(context = LocalContext.current).create(HomeViewModel::class.java)
    val state = viewModel.expenses.collectAsState(initial = emptyList())
    val searchText = remember { mutableStateOf("") }

    Scaffold(topBar = {
        Column {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Text(
                    text = "Transactions", fontSize = 18.sp, color = Color.Black,
                    style = MaterialTheme.typography.labelLarge
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                value = searchText.value, onValueChange = {searchText.value = it},
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )
        }
    },) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TransactionList(
                modifier = Modifier.weight(1f),
                state.value.filter { it.title.contains(searchText.value, ignoreCase = true) },
                "History",
                onSeeAll = {}
            )
            Button(
                onClick = { navController.navigate("/addExpense") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    zinc
                ),
            ) {
                Text(text = "Add Transaction")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactonScreen() {
    TransactionScreen(rememberNavController())
}