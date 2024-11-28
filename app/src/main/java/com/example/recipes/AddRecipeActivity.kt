package com.example.recipes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import com.example.recipes.data.RecipeName
import com.example.recipes.databinding.ActivityAddRecipeBinding
import com.example.recipes.databinding.ActivityDetailBinding
import com.example.recipes.utis.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddRecipeBinding

    lateinit var recipe: RecipeName

    lateinit var recipeList: List<RecipeName>


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

        bottonGuardar()

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
                        println("datos cargados $recipeList")
                        //adapter.updateItems(recipeList)
                    }
                } catch (e: Exception) {
                    Log.e("API", e.stackTraceToString())

                }
            }
        }

    fun bottonGuardar() {
        binding.saveButton.setOnClickListener(){
            // obtener el texto del editext
            val inputValue = binding.myTextInputEditText.text.toString()

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

            println(" agregar el valor, todo es corecto $inputValue")
            //recipeList.add(inputValue)
            Toast.makeText(this, "receta agregada: $inputValue", Toast.LENGTH_SHORT).show()
           binding.myTextInputEditText.text?.clear()


        }
    }

}



