package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.*
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


class ControlDetailsViewHolder(
    private val binding: ItemInventarioDetailsBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(controlInventario: ControlInventarioDetalles, onItemClicked: (ControlInventarioDetalles, imageView: ImageView, View)->Unit){
        binding.productoNombre.text=controlInventario.producto_nombre
        binding.observacionControl.text=controlInventario.observacion
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
    }
}