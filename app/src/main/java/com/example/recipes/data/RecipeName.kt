package com.example.recipes.data



data class RecipeName (
    // estructura de la api, por cada llave

    val id:Int,
    val name:String,
    val ingredients: Array<String>,
    val image: String
){
    companion object {
        const val TABLE_NAME = "RecipeBd"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME= "name"
        const val COLUMN_IMAGE = "image"
    }
}

// estructura de la api, por cada llave, para listar todas las recertas, desconocemos el id
data class RecipeNameList(
    val recipes:List<RecipeName>
) {

}