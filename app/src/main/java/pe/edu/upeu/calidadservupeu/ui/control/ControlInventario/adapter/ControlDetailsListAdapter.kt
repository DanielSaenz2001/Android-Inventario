package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.*
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlCreateViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlDetailsViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlViewHolder
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.viewholder.RegulacionesViewHolder

class ControlDetailsListAdapter(
    private val onItemClicked: (ControlInventarioDetalles, imageView: ImageView, View)->Unit
): ListAdapter<ControlInventarioDetalles, ControlDetailsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlDetailsViewHolder =
        ControlDetailsViewHolder(
            ItemInventarioDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ControlDetailsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ControlInventarioDetalles>() {
            override fun areItemsTheSame(
                oldItem: ControlInventarioDetalles,
                newItem: ControlInventarioDetalles
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ControlInventarioDetalles,
                newItem: ControlInventarioDetalles
            ): Boolean = oldItem == newItem
        }
    }
}