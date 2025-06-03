package com.example.expensetracker.features.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.Green
import com.example.expensetracker.ui.theme.zinc
import com.example.expensetracker.ui.theme.zinc_Light
import com.example.expensetracker.utils.Utils
import com.example.expensetracker.viewmodel.home.HomeViewModel
import com.example.expensetracker.viewmodel.home.HomeViewModelFactory

@Composable
fun HomeScreen(navController: NavController) {

    val viewModel =
        HomeViewModelFactory(context = LocalContext.current).create(HomeViewModel::class.java)

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar, add) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                })

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(topBar.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Good Afternoon", fontSize = 16.sp, color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Junaid Javed",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_notify),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp)
                )

            }

            val state = viewModel.expenses.collectAsState(initial = emptyList())
            val balance = viewModel.getBalance(state.value)
            val expense = viewModel.totalExpense(state.value)
            val income = viewModel.totalIncome(state.value)
            CartItem(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, balance = balance, expense = expense, income = income
            )

            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }, list = state.value,
                onSeeAll = {
                    navController.navigate("/transaction")
                }
            )
        }

    }

}

@Composable
fun CartItem(modifier: Modifier, balance: String, expense: String, income: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(brush = Brush.linearGradient(listOf(zinc, zinc_Light)))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Total Balance",
                        fontSize = 18.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Image(
                        painter = painterResource(id = R.drawable.chevron_down),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = balance, fontSize = 24.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White, modifier = Modifier.padding(top = 8.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_down),
                        contentDescription = null
                    )
                    Text(
                        text = "Income", fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White, modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_up),
                        contentDescription = null
                    )
                    Text(
                        text = "Expenses", fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White, modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = income, fontSize = 20.sp,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White, modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = expense, fontSize = 20.sp,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White, modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, title: String = "Recent Transaction", onSeeAll: () -> Unit = {}) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title, fontSize = 20.sp,
                    style = MaterialTheme.typography.labelMedium,
                )
                if (title == "Recent Transaction") {
                    Text(
                        text = "See All",
                        fontSize = 16.sp,
                        color = zinc,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .clickable{
                                onSeeAll()
                            },
                    )
                }
            }
        }
        items(list.size) {
            TransactionItem(
                title = list[it].title,
                amount = list[it].amount.toString(),
                icon = if (list[it].type == "Income") R.drawable.ic_expense else R.drawable.ic_woek,
                date = Utils.dateFormatter(list[it].date.toLong()),
                color = if (list[it].type == "Income") Green else Color.Red
            )
        }
    }

}

@Composable
fun TransactionItem(title: String, amount: String, icon: Int, date: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = icon), contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    letterSpacing = 0.02.em,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = date, fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Text(
            text = amount,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}

