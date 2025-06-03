package com.example.expensetracker.viewmodel.stats

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.features.expense.ExpenseSummary
import com.example.expensetracker.utils.Utils
import com.github.mikephil.charting.data.Entry

class StatsViewModel(val dao: ExpenseDao) : ViewModel() {

    val dailyExpenses = dao.getAllExpenseByDate()
    val topExpenses = dao.getTopExpenses()

    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry> {
        val list = mutableListOf<Entry>()
        for (entry in entries) {
            val dateFormat = entry.date
            list.add(Entry(dateFormat.toFloat(), entry.total_amount.toFloat()))
        }
        return list
    }
}
