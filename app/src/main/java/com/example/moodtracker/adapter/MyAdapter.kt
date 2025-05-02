package com.example.moodtracker.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.Mood

class MyAdapter (
    val itemList: MutableList<Mood>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<MyAdapter.ItemViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(mood: Mood)
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemFeel: TextView = itemView.findViewById(R.id.mood_item_feel)
        val itemType: TextView = itemView.findViewById(R.id.mood_item_type)
        val itemRating: RatingBar = itemView.findViewById(R.id.mood_item_rating)
        val itemEmoji: TextView = itemView.findViewById(R.id.mood_item_emoji)
        val itemSchema: LinearLayout = itemView.findViewById(R.id.mood_schema)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemFeel.text = currentItem.feeling
        holder.itemType.text = currentItem.type
        holder.itemEmoji.text = when(currentItem.feeling){
            "Happy" -> ":D"
            "Content" -> ":)"
            "Sad" -> ":("
            else -> "o_O"
        }
        holder.itemRating.rating = currentItem.rating

        holder.itemSchema.setBackgroundResource(
            when(currentItem.feeling){
                "Happy" -> R.color.happy
                "Content" -> R.color.content
                "Sad" -> R.color.sad
                else -> R.color.confused
            }
        )

        holder.itemView.setOnClickListener{
            listener.onItemClick(currentItem)
        }
    }
    override fun getItemCount(): Int = itemList.size
}