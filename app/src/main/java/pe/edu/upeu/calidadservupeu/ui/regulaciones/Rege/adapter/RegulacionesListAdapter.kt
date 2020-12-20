package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder.ProduccionViewHolder
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.viewholder.RegulacionesViewHolder

class RegulacionesListAdapter(
    private val onItemClicked: (Regulaciones, imageView: ImageView, View)->Unit
): ListAdapter<Regulaciones, RegulacionesViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegulacionesViewHolder =
        RegulacionesViewHolder(
            ItemRegulacionesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RegulacionesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Regulaciones>() {
            override fun areItemsTheSame(
                oldItem: Regulaciones,
                newItem: Regulaciones
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Regulaciones,
                newItem: Regulaciones
            ): Boolean = oldItem == newItem
        }
    }
}