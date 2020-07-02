package com.pratham.dse.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
@Entity
class Order() : Serializable {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var orderNumber: Int = 0
    var orderDateTime: String? = ""
    var totalAmount: String? = ""


}