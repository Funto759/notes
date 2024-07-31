package com.example.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Notes
import com.example.notes.R

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Notes>(){
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = differ.currentList[position]
        holder.itemView.apply {
            var title = findViewById<TextView>(R.id.title)
            var content = findViewById<TextView>(R.id.tv_note_content)
            var date = findViewById<TextView>(R.id.tv_note_date)

            title.text = notes.title
            content.text = notes.content
            date.text = notes.date

            setOnClickListener {
                onItemClickListener?.let { it(notes) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Notes) -> Unit){

        onItemClickListener = listener
    }

    private var onItemClickListener : ((Notes) -> Unit)? = null


    }
