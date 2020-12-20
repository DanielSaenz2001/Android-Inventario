package pe.edu.upeu.calidadservupeu.ui.InMatePrima.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemIntMateBinding
import pe.edu.upeu.calidadservupeu.model.IntMate
import pe.edu.upeu.calidadservupeu.model.ProductDespachos

class InMateViewHolder(
    private val binding: ItemIntMateBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(mate: IntMate, onItemClicked: (IntMate, ImageView, View)->Unit){
        binding.proveedorName.text=mate.proveedor
        binding.factura.text= mate.nFactura
        binding.fecha.text=mate.fecha
        binding.imageView.load(mate.proveedor_imagen){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.imageView.setOnClickListener {
            Log.i("LLEGA_P", "Imagen:"+mate.id)
            onItemClicked(mate, binding.imageView, binding.imageView)
        }
    }
}