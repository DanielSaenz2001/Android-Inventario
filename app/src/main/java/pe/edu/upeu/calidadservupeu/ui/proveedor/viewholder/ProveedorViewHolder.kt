package pe.edu.upeu.calidadservupeu.ui.proveedor.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProveedorBinding
import pe.edu.upeu.calidadservupeu.model.Proveedores

class ProveedorViewHolder(
    private val binding: ItemProveedorBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(proveedor: Proveedores, onItemClicked: (Proveedores, ImageView, View)->Unit){
        binding.proveedorName.text=proveedor.nombre
        binding.proveedorRuc.text=proveedor.ruc
        // producto.nombre === url de la imagen ="https://img.imagenescool.com/ic/hola/hola_032.jpg"
        binding.imageView.load(proveedor.imagen_proveedores){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.imageView.setOnClickListener {
            Log.i("LLEGA_P", "Imagen:"+proveedor.id)
            onItemClicked(proveedor, binding.imageView, binding.imageView)
        }
        binding.proveedorName.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+proveedor.id)
        }

    }
}