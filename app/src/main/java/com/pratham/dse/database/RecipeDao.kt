package com.pratham.dse.database

import androidx.room.*
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)


    @Query("SELECT * FROM Recipe")
    fun fetchAllRecipe(): MutableList<Recipe>


    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("Delete FROM Recipe")
    fun clearCart()

}