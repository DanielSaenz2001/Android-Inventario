package pe.edu.upeu.calidadservupeu.ui.producto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoBinding
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.producto.viewholder.ProductoViewHolder

class ProductoListAdapter(
    private val onItemClicked: (Producto, ImageView, View)->Unit
):ListAdapter<Producto, ProductoViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder =
        ProductoViewHolder(
        ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) =holder.bind(getItem(position), onItemClicked)

  companion object{
      private val DIFF_CALLBACK=object :DiffUtil.ItemCallback<Producto>(){
          override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean = oldItem.id==newItem.id
          override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean = oldItem==newItem
      }
  }
}