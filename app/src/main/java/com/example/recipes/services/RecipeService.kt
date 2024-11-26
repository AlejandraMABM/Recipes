package com.example.recipes.services

import com.example.recipes.data.RecipeName
import com.example.recipes.data.RecipeNameList
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

    @GET("recipes/")
    suspend fun findAllRecipes() : RecipeNameList

    //@GET ("recipes/search/{name}")
    //suspend fun findRecipesByName(@Path("name") query: String) : RecipeName


    @GET ("recipes/{id}")
    suspend fun findRecipesById(@Path("id") int: Int): RecipeName
}
