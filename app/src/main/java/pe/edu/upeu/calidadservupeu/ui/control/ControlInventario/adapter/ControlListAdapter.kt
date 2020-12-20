package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemControlBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionViewHolder
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder.ControlViewHolder
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.viewholder.RegulacionesViewHolder

class ControlListAdapter(
    private val onItemClicked: (ControlInventario, imageView: ImageView, View)->Unit
): ListAdapter<ControlInventario, ControlViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlViewHolder =
        ControlViewHolder(
            ItemControlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ControlViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ControlInventario>() {
            override fun areItemsTheSame(
                oldItem: ControlInventario,
                newItem: ControlInventario
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ControlInventario,
                newItem: ControlInventario
            ): Boolean = oldItem == newItem
        }
    }
}