package pe.edu.upeu.calidadservupeu.ui.InMatePrima.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemIntMateBinding
import pe.edu.upeu.calidadservupeu.model.IntMate
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.viewholder.InMateViewHolder

class InMateListAdapter(
    private val onItemClicked: (IntMate,ImageView, View)->Unit
): ListAdapter<IntMate, InMateViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InMateViewHolder =
        InMateViewHolder(
            ItemIntMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: InMateViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IntMate>() {
            override fun areItemsTheSame(
                oldItem: IntMate,
                newItem: IntMate
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: IntMate,
                newItem: IntMate
            ): Boolean = oldItem == newItem
        }
    }
}