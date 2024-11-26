package com.example.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.adapters.RecipeAdapter
import com.example.recipes.data.RecipeName
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.utis.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var recipeList: List<RecipeName> = emptyList()

    lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = RecipeAdapter(recipeList) {
            position ->
            val recipe = recipeList[position]
            navigateToDetail(recipe)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)



        cargarDatos()




    }

    private fun cargarDatos() {
        val service = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.findAllRecipes()

                CoroutineScope(Dispatchers.Main).launch {
                    recipeList = result.recipes
                    adapter.updateItems(recipeList)
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())

            }
        }
    }

    // terminar la funcion navigate to detail hecha por nosotros

    private fun navigateToDetail(recipe: RecipeName) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_RECIPE_ID,recipe.id)
        println("enviando el recipe id ${recipe.id} ")
        startActivity(intent)

    }
}