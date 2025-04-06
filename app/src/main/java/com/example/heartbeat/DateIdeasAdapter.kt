package com.example.heartbeat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import androidx.core.content.ContextCompat

class DateIdeasAdapter(
    private val dateIdeas: List<DateIdea>,
    private val onPrevClick: () -> Unit,
    private val onNextClick: () -> Unit
) : RecyclerView.Adapter<DateIdeasAdapter.DateViewHolder>() {

    class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.dateImage)
        val titleText: TextView = view.findViewById(R.id.titleText)
        val descriptionText: TextView = view.findViewById(R.id.descriptionText)
        val prevButton: ImageButton = view.findViewById(R.id.prevButton)
        val nextButton: ImageButton = view.findViewById(R.id.nextButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_idea, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        try {
            val dateIdea = dateIdeas[position]
            
            // Set image with error handling
            try {
                ContextCompat.getDrawable(holder.itemView.context, dateIdea.imageResId)?.let {
                    holder.imageView.setImageDrawable(it)
                } ?: run {
                    Log.e("DateIdeasAdapter", "Drawable resource not found for id: ${dateIdea.imageResId}")
                    // Set a default image or placeholder
                    holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            } catch (e: Exception) {
                Log.e("DateIdeasAdapter", "Error loading image: ${e.message}")
                holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            // Set texts with null checks
            holder.titleText.text = dateIdea.title.orEmpty()
            holder.descriptionText.text = dateIdea.description.orEmpty()
            
            holder.prevButton.setOnClickListener { onPrevClick() }
            holder.nextButton.setOnClickListener { onNextClick() }

        } catch (e: Exception) {
            Log.e("DateIdeasAdapter", "Error binding view holder: ${e.message}")
        }
    }

    override fun getItemCount() = dateIdeas.size
}

data class DateIdea(
    val imageResId: Int,
    val title: String,
    val description: String
) 