package com.example.expensetracker.viewmodel.home

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.utils.Utils

class HomeViewModel(dao: ExpenseDao) : ViewModel() {

    val expenses = dao.getAllExpenses()

    fun getBalance(list: List<ExpenseEntity>): String {
        var balance = 0.0
        list.forEach {
            if (it.type == "Income") {
                balance += it.amount
            } else {
                balance -= it.amount
            }
        }
        return "$ ${Utils.formatToDecimal(balance)}"
    }

    fun totalExpense(list: List<ExpenseEntity>): String {
        var balance = 0.0
        list.forEach {
            if (it.type == "Expense") {
                balance += it.amount
            }
        }
        return "$ ${Utils.formatToDecimal(balance)}"
    }

    fun totalIncome(list: List<ExpenseEntity>): String {
        var balance = 0.0
        list.forEach {
            if (it.type == "Income") {
                balance += it.amount
            }
        }
        return "$ ${Utils.formatToDecimal(balance)}"
    }
}