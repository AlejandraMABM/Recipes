package com.example.recipes.data



data class RecipeName (
    // estructura de la api, por cada llave

    val id:Int,
    val name:String,
    val ingredients: List<String>,
    val image: String
){
}

// estructura de la api, por cada llave, para listar todas las recertas, desconocemos el id
data class RecipeNameList(
    val recipes:List<RecipeName>
) {

}