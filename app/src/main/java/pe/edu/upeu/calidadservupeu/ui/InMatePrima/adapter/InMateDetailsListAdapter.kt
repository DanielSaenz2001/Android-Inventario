package pe.edu.upeu.calidadservupeu.ui.InMatePrima.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemIntMateDetailsBinding
import pe.edu.upeu.calidadservupeu.model.IntMateDetalles
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.viewholder.InMateDetailsViewHolder

class InMateDetailsListAdapter(
    private val onItemClicked: (IntMateDetalles, View)->Unit
): ListAdapter<IntMateDetalles, InMateDetailsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InMateDetailsViewHolder =
        InMateDetailsViewHolder(
            ItemIntMateDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: InMateDetailsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IntMateDetalles>() {
            override fun areItemsTheSame(
                oldItem: IntMateDetalles,
                newItem: IntMateDetalles
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: IntMateDetalles,
                newItem: IntMateDetalles
            ): Boolean = oldItem == newItem
        }
    }
}