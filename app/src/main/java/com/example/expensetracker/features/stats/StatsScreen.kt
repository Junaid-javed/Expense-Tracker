package com.example.expensetracker.features.stats

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.viewmodel.stats.StatsViewModel
import com.example.expensetracker.viewmodel.stats.StatsViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import androidx.core.graphics.toColorInt
import com.example.expensetracker.features.home.TransactionList
import com.example.expensetracker.utils.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun StatsScreen(navController: NavController) {
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left), contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Text(
                text = "Statistics", fontSize = 18.sp, color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    }) {
        val viewModel =
            StatsViewModelFactory(context = navController.context).create(StatsViewModel::class.java)
        val dataState = viewModel.dailyExpenses.collectAsState(initial = emptyList())
        val topExpenses = viewModel.topExpenses.collectAsState(initial = emptyList())
        Column(modifier = Modifier.padding(it)) {
            val entries = viewModel.getEntriesForChart(dataState.value)
            LineChart(entries = entries)
            Spacer(modifier = Modifier.height(16.dp))
            TransactionList(modifier = Modifier, topExpenses.value, "Top Expenses", onSeeAll = {})
        }
    }
}

@Composable
fun LineChart(entries: List<Entry>) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            val view = LayoutInflater.from(context).inflate(R.layout.stats_line_chart, null)
            view
        }, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { view ->
        val lineChart = view.findViewById<LineChart>(R.id.lineChart)

        val lineDataSet = LineDataSet(entries, "Daily Expenses").apply {
            color = "#FF2F7E79".toColorInt()
            valueTextColor = android.graphics.Color.BLACK
            lineWidth = 3f
            axisDependency = YAxis.AxisDependency.LEFT
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 12f
            valueTextColor = "#FF2F7E79".toColorInt()
            val drawable = context.getDrawable(R.drawable.bg_gradient)
            drawable.let {
                fillDrawable = it
            }
        }
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String? {
                return Utils.dateFormatterForChart(value.toLong())
            }
        }
        lineChart.data = LineData(lineDataSet)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate()
    }
}