package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoDetailsBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.ProductosDespachosDetalles
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.viewholder.DespachosDetailsViewHolder
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder.CarritoViewHolder

class DespachosDetailsListAdapter(
    private val onItemClicked: (ProductosDespachosDetalles, ImageView, View)->Unit
): ListAdapter<ProductosDespachosDetalles, DespachosDetailsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespachosDetailsViewHolder =
        DespachosDetailsViewHolder(
            ItemProductoDespachoDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: DespachosDetailsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductosDespachosDetalles>() {
            override fun areItemsTheSame(
                oldItem: ProductosDespachosDetalles,
                newItem: ProductosDespachosDetalles
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductosDespachosDetalles,
                newItem: ProductosDespachosDetalles
            ): Boolean = oldItem == newItem
        }
    }

}