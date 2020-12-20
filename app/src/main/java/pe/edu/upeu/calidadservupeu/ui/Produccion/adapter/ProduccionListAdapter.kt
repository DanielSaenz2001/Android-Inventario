package pe.edu.upeu.calidadservupeu.ui.Produccion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionViewHolder

class ProduccionListAdapter(
    private val onItemClicked: (Produccion, imageView: ImageView, View)->Unit
): ListAdapter<Produccion, ProduccionViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduccionViewHolder =
        ProduccionViewHolder(
            ItemProduccionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProduccionViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Produccion>() {
            override fun areItemsTheSame(
                oldItem: Produccion,
                newItem: Produccion
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Produccion,
                newItem: Produccion
            ): Boolean = oldItem == newItem
        }
    }
}