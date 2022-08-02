package com.example.fortunegame.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilItem : DiffUtil.ItemCallback<SlotElement>() {
    override fun areItemsTheSame(oldItem: SlotElement, newItem: SlotElement): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: SlotElement, newItem: SlotElement): Boolean {
        return oldItem == oldItem
    }
}