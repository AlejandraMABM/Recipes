package com.example.recipes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recipes.data.RecipeName
import com.example.recipes.data.RecipeNameAPI
import com.example.recipes.databinding.ActivityDetailBinding
import com.example.recipes.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECIPE_ID = "RECIPE_ID"
    }

    lateinit var binding: ActivityDetailBinding

    lateinit var recipe: RecipeNameAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // corregir el problema el id se envía como string y es entero ---corregirlo
        val idRecibido = intent.getIntExtra(EXTRA_RECIPE_ID,-1)!!

        if (idRecibido != -1)
        {  println("id recibido correctamente $idRecibido")}
        else {
            println("error no se escribión un id válido")
        }

        println("recibiendo el id $idRecibido")

        getRecipe(idRecibido)
    }

    private fun getRecipe(id: Int) {

        val service = RetrofitProvider.getRetrofit()
        println("service $service   $id\"")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                recipe = service.findRecipesById(id)

                CoroutineScope(Dispatchers.Main).launch {
                   binding.detailRecipeNameTextView.text = recipe.name
                    Picasso.get().load(recipe.image).into(binding.detailRecipeImageView)

                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }




}
