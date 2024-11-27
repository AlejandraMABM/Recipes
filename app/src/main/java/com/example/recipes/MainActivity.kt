package com.example.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

       // searchRecipe("a")


    }



    private fun cargarDatos() {

        val service = RetrofitProvider.getRetrofit()
        binding.loadingProgressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.findAllRecipes()

                CoroutineScope(Dispatchers.Main).launch {
                    binding.loadingProgressBar.visibility = View.GONE
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
        startActivity(intent)

    }



    private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("Búsqueda de Recipes")
            .setMessage("El recipe no existe, volver a cargar la lista")
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                adapter.updateItems(recipeList)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }


    private fun searchRecipe(query: String) {
        val filteredList = recipeList.filter { it.name.contains(query, true) }
        if (filteredList.isEmpty()) {
            // Mostrar alerta de que no se han encontrado resultados
            showAlert()
        }
        adapter.updateItems(filteredList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)!!
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                println("recibiendo los datos de busquqeda $query")
                //searchRecipe(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("nex text $newText")
                searchRecipe(newText)
                return true
            }
        })

        return true
    }


}