package pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.model.Produccion


class ProduccionViewHolder(
    private val binding: ItemProduccionBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(produccion: Produccion, onItemClicked: (Produccion, imageView: ImageView, View)->Unit){
        binding.txtProduccionResponsable.text=produccion.responsable_nombre
        binding.txtFechaProduccion.text= produccion.fecha
        binding.txtProductoProduccion.text=produccion.nombre_producto
        binding.textView13.text=produccion.cantidad_producto.toString()
        binding.imageView.load(produccion.imagen_producto){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.btnVerProduccion.setOnClickListener {
            onItemClicked(produccion, binding.imageView, binding.btnVerProduccion)
        }
    }
}