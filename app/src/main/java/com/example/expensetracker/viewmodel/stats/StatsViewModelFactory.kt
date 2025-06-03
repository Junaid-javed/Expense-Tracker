package com.example.expensetracker.viewmodel.stats

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDatabase

class StatsViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(ExpenseDatabase.getDatabase(context).expenseDao()) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}