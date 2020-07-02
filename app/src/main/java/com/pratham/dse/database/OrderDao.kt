package com.pratham.dse.database

import androidx.room.*

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
@Dao
interface OrderDao {

    @Insert
    fun insertOrder(order: Order)


    @Query("SELECT * FROM `Order`")
    fun fetchAllOrder(): MutableList<Order>


    @Update
    fun update(order: Order)

    @Delete
    fun delete(order: Order)

}