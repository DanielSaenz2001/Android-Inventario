package pe.edu.upeu.calidadservupeu.ui.producto.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoBinding
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

class ProductoViewHolder(
    private val binding: ItemProductoBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(producto: Producto, onItemClicked: (Producto, ImageView, View)->Unit){
        binding.productoTitle.text=producto.nombre
        binding.productoSubitem.text=""+producto.stock
        if(UtilsToken.tipo_actividad == "Crear_Despacho_Producto" || UtilsToken.tipo_actividad == "Crear_Produccion" || UtilsToken.tipo_actividad =="Regulaciones"){
            binding.btnSelectProducto.visibility=View.VISIBLE
        }
        binding.imageView.load(producto.imagen_producto){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        /*binding.root.setOnClickListener {
            onItemClicked(producto,binding.imageView, binding.root)
        }*/

        binding.imageView.setOnClickListener {
            Log.i("LLEGA_P", "Imagen:"+producto.id)
            onItemClicked(producto, binding.imageView, binding.imageView)
        }
        binding.btnSelectProducto.setOnClickListener {
            Log.i("LLEGA_P", "btnSelectProducto:"+producto.id)
            onItemClicked(producto, binding.imageView, binding.btnSelectProducto)
        }
        binding.productoTitle.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+producto.id)
        }

    }
}