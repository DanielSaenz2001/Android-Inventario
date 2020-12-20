package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemMaterialesEmbarqueBinding
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.viewholder.MaterialesEmpaqueViewHolder

class MaterialesEmpaqueListAdapter(
    private val onItemClicked: (MaterialesEmpaque, ImageView, View)->Unit
): ListAdapter<MaterialesEmpaque, MaterialesEmpaqueViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialesEmpaqueViewHolder =
        MaterialesEmpaqueViewHolder(
            ItemMaterialesEmbarqueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MaterialesEmpaqueViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MaterialesEmpaque>() {
            override fun areItemsTheSame(
                oldItem: MaterialesEmpaque,
                newItem: MaterialesEmpaque
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MaterialesEmpaque,
                newItem: MaterialesEmpaque
            ): Boolean = oldItem == newItem
        }
    }
}