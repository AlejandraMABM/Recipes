package com.example.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.data.RecipeName
import com.example.recipes.databinding.ItemRecipeBinding

class RecipeAdapter(var items: List<RecipeName>, val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = items[position]
        holder.render(recipe)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }


    override fun getItemCount(): Int {
        return items.size

    }

    fun updateItems(items: List<RecipeName>) {
        this.items = items
        notifyDataSetChanged()
    }
}


class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(recipe: RecipeName) {
        binding.nameRecipeTextView.text = recipe.name
    }

}
