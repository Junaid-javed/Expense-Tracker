package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_db"

        @JvmStatic
        fun getDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                 //   initData(context)
                }

                fun initData(context: Context) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val expenseDao = getDatabase(context).expenseDao()
                        expenseDao.insertExpense(
                            ExpenseEntity(
                                1,
                                "Salary",
                                "Income",
                                5000.0,
                                System.currentTimeMillis().toString(),
                                "Salary"
                            )
                        )
                        expenseDao.insertExpense(
                            ExpenseEntity(
                                2,
                                "Paypal",
                                "Expense",
                                200.0,
                                System.currentTimeMillis().toString(),
                                "Paypal"
                            )
                        )
                        expenseDao.insertExpense(
                            ExpenseEntity(
                                3,
                                "Transportation",
                                "Expense",
                                100.0,
                                System.currentTimeMillis().toString(),
                                "Transportation"
                            )
                        )
                        expenseDao.insertExpense(
                            ExpenseEntity(
                                4,
                                "Upwork",
                                "Expense",
                                500.0,
                                System.currentTimeMillis().toString(),
                                "Upwork"
                            )
                        )

                    }
                }
            }).build()
        }
    }


}