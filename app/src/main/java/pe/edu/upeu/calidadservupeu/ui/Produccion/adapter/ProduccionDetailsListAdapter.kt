package pe.edu.upeu.calidadservupeu.ui.Produccion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoDetailsBinding
import pe.edu.upeu.calidadservupeu.model.ProduccionDetalles
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionDetailsViewHolder

class ProduccionDetailsListAdapter(
    private val onItemClicked: (ProduccionDetalles, View)->Unit
): ListAdapter<ProduccionDetalles, ProduccionDetailsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduccionDetailsViewHolder =
        ProduccionDetailsViewHolder(
            ItemProductoDespachoDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: ProduccionDetailsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProduccionDetalles>() {
            override fun areItemsTheSame(
                oldItem: ProduccionDetalles,
                newItem: ProduccionDetalles
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProduccionDetalles,
                newItem: ProduccionDetalles
            ): Boolean = oldItem == newItem
        }
    }

}