package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemDespachoBinding
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.viewholder.DespachosViewHolder

class DespachosListAdapter(
    private val onItemClicked: (ProductDespachos, View)->Unit
): ListAdapter<ProductDespachos, DespachosViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespachosViewHolder =
        DespachosViewHolder(
            ItemDespachoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DespachosViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductDespachos>() {
            override fun areItemsTheSame(
                oldItem: ProductDespachos,
                newItem: ProductDespachos
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductDespachos,
                newItem: ProductDespachos
            ): Boolean = oldItem == newItem
        }
    }
}