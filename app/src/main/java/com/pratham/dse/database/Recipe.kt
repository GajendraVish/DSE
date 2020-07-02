package com.pratham.dse.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */
@Entity
class Recipe() : Serializable {


    @PrimaryKey()
    var id: Int = 0
    var rate: Int = 0
    var name: String? = null
    var description: String? = null
    var image: String? = null


}