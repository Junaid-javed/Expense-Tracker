package com.example.expensetracker.viewmodel.expense


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDatabase

class AddExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(ExpenseDatabase.getDatabase(context).expenseDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}