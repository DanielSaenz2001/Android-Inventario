package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemControlBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemInventarioBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlCreateViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlViewHolder
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.viewholder.RegulacionesViewHolder

class ControlCreateListAdapter(
    private val onItemClicked: (ControlInventarioDetalles, imageView: ImageView, View)->Unit
): ListAdapter<ControlInventarioDetalles, ControlCreateViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlCreateViewHolder =
        ControlCreateViewHolder(
            ItemInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ControlCreateViewHolder, position: Int) =
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