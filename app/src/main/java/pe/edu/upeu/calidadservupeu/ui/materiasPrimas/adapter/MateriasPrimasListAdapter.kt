package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemMateriasPrimasBinding
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder.CarritoViewHolder
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder.MateriasPrimasViewHolder

class MateriasPrimasListAdapter(
    private val onItemClicked: (MateriasPrimas, ImageView, View)->Unit
): ListAdapter<MateriasPrimas, MateriasPrimasViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriasPrimasViewHolder =
        MateriasPrimasViewHolder(
            ItemMateriasPrimasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MateriasPrimasViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MateriasPrimas>() {
            override fun areItemsTheSame(
                oldItem: MateriasPrimas,
                newItem: MateriasPrimas
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MateriasPrimas,
                newItem: MateriasPrimas
            ): Boolean = oldItem == newItem
        }
    }
}