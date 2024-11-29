package com.example.recipes.services

import com.example.recipes.data.RecipeName
import com.example.recipes.data.RecipeNameAPI
import com.example.recipes.data.RecipeNameList
import com.example.recipes.data.RecipeNameListAPI
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("recipes/")
    suspend fun findAllRecipes(@Query("limit") limit: Int = 0) : RecipeNameListAPI



    @GET ("recipes/search")
    suspend fun findRecipesByName(@Query("q") query: String) : RecipeNameListAPI


    @GET ("recipes/{id}")
    suspend fun findRecipesById(@Path("id") id: Int): RecipeNameAPI
}
