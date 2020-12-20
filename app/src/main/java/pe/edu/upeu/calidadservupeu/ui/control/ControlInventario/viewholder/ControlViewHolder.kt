package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.viewholder


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemControlBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


class ControlViewHolder(
    private val binding: ItemControlBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(controlInventario: ControlInventario, onItemClicked: (ControlInventario, imageView: ImageView, View)->Unit){
        binding.responsable.text=controlInventario.responsable
        binding.fecha.text=controlInventario.fecha_fin
        binding.imageView.load(controlInventario.imagen_user){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.imageView.setOnClickListener {
            onItemClicked(controlInventario, binding.imageView, binding.imageView)
        }
    }
}