package com.example.recipes

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recipes.data.RecipeName
import com.example.recipes.databinding.ActivityAddRecipeBinding
import com.example.recipes.utils.RetrofitProvider
import com.example.todolist.data.providers.RecipeNameDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddRecipeBinding

    lateinit var recipe: RecipeName

    lateinit var recipeList: List<RecipeName>

    lateinit var recipeNameDao : RecipeNameDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // cargamo la llamada a activity add recipe

        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // verificar si el recipe existe, para no repetirlo
        cargarDatos()

        saveRecipe()

    }


        // listener para el boton guardar

        // cargar datps
        fun cargarDatos() {

            val service = RetrofitProvider.getRetrofit()


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = service.findAllRecipes()

                    CoroutineScope(Dispatchers.Main).launch {

                        recipeList = result.recipes
                        var tagsString = recipe.ingredients.joinToString(",")
                        println("utilizamos una variable  string para recibir los datos de array de ingredientes $tagsString")
                        println("datos cargados $recipeList")
                        //adapter.updateItems(recipeList)
                    }
                } catch (e: Exception) {
                    Log.e("API", e.stackTraceToString())

                }
            }
        }

    fun saveRecipe() {
        binding.saveButton.setOnClickListener(){
            // obtener el texto del editext
            val inputValue = binding.myTextInputEditText.text.toString()

         // colocar variable que acepta los nuevos ingredientes


            println("botton guardar $inputValue")

            // validar que no este vacio
            if(inputValue.isBlank()) {
                binding.myTextInputLayout.error = "el campo no puede estar vacio"
                return@setOnClickListener
            }

            // verificar si el valor ya esta en la lista

            if (recipeList.any { it.name.equals(inputValue, true) }) {
                binding.myTextInputLayout.error = "el valor ya está en la lista de recetas"
                return@setOnClickListener
            }
            // si pasa las validaciones limpia errores y agrega el valor

            binding.myTextInputLayout.error = null


            recipe.name = inputValue

           // transformas la lista de elementos que intriduce el usuario para convertirlo en un array de string
            // obtener el texto introducido por el usuario
            val inputField = binding.inputIngredients.text.toString()

            println(" ingredientes introducidos por el usuario  $inputField")
            val inputText = inputField


            println(" ingredientes a convertir $inputText")

            // dividir el texto en una lista de elementos separados por coma
            val elements = inputText.split(",").map { it.trim() }

            elements.forEach() {
                println(" ver elementos por lista string $elements")
            }


            println(" agregar el valor, todo es corecto $inputValue ${recipe.name}")
            recipeNameDao.insert(recipe)
            recipe.ingredients = elements

            Toast.makeText(this, "receta agregada: $inputValue", Toast.LENGTH_SHORT).show()
           binding.myTextInputEditText.text?.clear()


        }
        finish()
    }

}



