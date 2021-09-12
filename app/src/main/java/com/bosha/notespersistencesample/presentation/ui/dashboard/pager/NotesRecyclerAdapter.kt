package com.bosha.notespersistencesample.presentation.ui.dashboard.pager

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bosha.notespersistencesample.databinding.NoteCardItemBinding
import com.bosha.notespersistencesample.domain.entities.Note

class NotesRecyclerAdapter :
    ListAdapter<Note, NotesRecyclerAdapter.ViewHolderNotes>(
        DiffCallback()
    ) {
    private var onEdit: ((note: Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNotes {
        val binding = NoteCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderNotes(binding).apply {
            binding.root.setOnClickListener {
                onEdit?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderNotes, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnEditListener(action: (note: Note) -> Unit) {
        onEdit = action
    }

    class ViewHolderNotes(
        private val binding: NoteCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.apply {
                mainTitle.text = item.title
                ivColor.setColorFilter(ivColor.context.getColor(item.colorId))
                tvPriority.text = Note.Priority.values()[item.priority].name
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        newItem == oldItem

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        newItem.title == oldItem.title
}