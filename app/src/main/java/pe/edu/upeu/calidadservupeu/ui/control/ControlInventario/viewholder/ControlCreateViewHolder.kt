package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemControlBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemInventarioBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


class ControlCreateViewHolder(
    private val binding: ItemInventarioBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(controlInventario: ControlInventarioDetalles, onItemClicked: (ControlInventarioDetalles, imageView: ImageView, View)->Unit){
        binding.productoNombre.text=controlInventario.producto_nombre
        binding.productoCantidad.text=controlInventario.producto_cantidad.toString()
        binding.productoUnidad.text=controlInventario.unidad
        if(controlInventario.estado =="vigente"){
            binding.colorEstado.setBackgroundColor(0xffd50000.toInt())
        }
        if(controlInventario.estado =="revisado"){
            binding.colorEstado.setBackgroundColor(0xff76ff03.toInt())
        }
        binding.imageView.load(controlInventario.producto_imagen){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.btnSelectInventario.setOnClickListener {
            onItemClicked(controlInventario, binding.imageView, binding.btnSelectInventario)
        }
    }
}