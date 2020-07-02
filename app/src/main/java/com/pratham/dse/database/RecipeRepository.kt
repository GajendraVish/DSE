package com.pratham.dse.database

import android.content.Context
import android.os.AsyncTask
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

class RecipeRepository(val context: Context) {

    fun insertRecipe(recipe: Recipe) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                AppDatabase.getDatabase(context).recipeDao().insertRecipe(recipe)
                return null
            }
        }.execute()
    }

    fun deleteRecipe(recipe: Recipe) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                AppDatabase.getDatabase(context).recipeDao().delete(recipe)
                return null
            }
        }.execute()
    }

    fun clearCart() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                AppDatabase.getDatabase(context).recipeDao().clearCart()
                return null
            }
        }.execute()
    }

    fun getAllRecipe(): MutableList<Recipe> {
        return GetAllRecipeTask().execute().get()
    }


    private inner class GetAllRecipeTask : AsyncTask<Void, Void, MutableList<Recipe>>() {
        override fun doInBackground(vararg url: Void): MutableList<Recipe> {
            return AppDatabase.getDatabase(context).recipeDao().fetchAllRecipe()
        }
    }
}