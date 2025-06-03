package com.example.expensetracker.viewmodel.expense

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

class AddExpenseViewModel(val dao: ExpenseDao) : ViewModel() {

    suspend fun addExpense(expense: ExpenseEntity): Boolean {
        try {
            dao.insertExpense(expense)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}