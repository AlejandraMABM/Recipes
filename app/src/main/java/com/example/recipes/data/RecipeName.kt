package com.example.recipes.data



data class RecipeName (
    val id:Int,
    val name:String,
    val ingredients: List<String>,
    val image: String
){
}

data class RecipeNameList(
    val recipes:List<RecipeName>
) {

}