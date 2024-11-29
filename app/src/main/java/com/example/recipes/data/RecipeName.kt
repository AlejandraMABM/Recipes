package com.example.recipes.data

data class RecipeName (
    // estructura de la api, por cada llave

    val id:Int,
    var name:String,
    var ingredients: String,
    val image: String
){
    companion object {
        const val TABLE_NAME = "RecipeBd"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME= "name"
        const val COLUMN_INGREDIENTS= "ingredients"
        const val COLUMN_IMAGE = "image"
        val COLUMN_NAMES = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_INGREDIENTS,
            COLUMN_IMAGE)
    }
}
data class RecipeNameAPIResponse(
    val id:Int,
    var name:String,
    var ingredients: List<String>,
    val image: String
)

// estructura de la api, por cada llave, para listar todas las recertas, desconocemos el id
data class RecipeNameList(
    val recipes:List<RecipeName>
)