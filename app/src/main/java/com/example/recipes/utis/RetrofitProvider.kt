package com.example.recipes.utis

import com.example.recipes.services.RecipeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit() : RecipeService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")  // colocamos la ruta de acceso a la api
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipeService::class.java)
        }
    }

}