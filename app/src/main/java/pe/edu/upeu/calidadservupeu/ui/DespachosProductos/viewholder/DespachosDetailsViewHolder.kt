package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoDetailsBinding
import pe.edu.upeu.calidadservupeu.model.ProductosDespachosDetalles


class DespachosDetailsViewHolder(
    private val binding: ItemProductoDespachoDetailsBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(despacho: ProductosDespachosDetalles, onItemClicked: (ProductosDespachosDetalles, ImageView, View)->Unit){
        binding.despachoName.text=""+despacho.producto_nombre
        binding.txtCantidadDespacho.setText(despacho.cantidad_producto.toString())
        binding.imageView.load(despacho.producto_imagen){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.btnVerEmpaque.setOnClickListener {
            onItemClicked(despacho, binding.imageView, binding.btnVerEmpaque)
        }
    }
}