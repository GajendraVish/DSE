package com.pratham.dse.database

import android.content.Context
import android.os.AsyncTask
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class OrderRepository(val context: Context) {

    fun insertOrder(order: Order) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                AppDatabase.getDatabase(context).orderDao().insertOrder(order)
                return null
            }
        }.execute()
    }

    fun deleteOrder(order: Order) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                AppDatabase.getDatabase(context).orderDao().delete(order)
                return null
            }
        }.execute()
    }

    fun getAllOrder(): MutableList<Order> {
        return GetAllOrderTask().execute().get()
    }


    private inner class GetAllOrderTask : AsyncTask<Void, Void, MutableList<Order>>() {
        override fun doInBackground(vararg url: Void): MutableList<Order> {
            return AppDatabase.getDatabase(context).orderDao().fetchAllOrder()
        }
    }
}