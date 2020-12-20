package pe.edu.upeu.calidadservupeu.ui.InMatePrima.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemIntMateDetailsBinding
import pe.edu.upeu.calidadservupeu.model.IntMateDetalles
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

class InMateDetailsViewHolder(
    private val binding: ItemIntMateDetailsBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(mate: IntMateDetalles, onItemClicked: (IntMateDetalles, View)->Unit){
        if(UtilsToken.ingreso == "prima"){
            binding.despachoName.text=mate.materia_prima_nombre
            binding.txtCantidadDespacho.text= mate.cantidad_prima.toString()
            binding.imageView.load(mate.materia_prima_imagen){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
            binding.btnDetalles.setOnClickListener {
                onItemClicked(mate, binding.btnDetalles)
            }
        }
        if(UtilsToken.ingreso=="empaque"){
            binding.despachoName.text=mate.material_empaque_nombre
            binding.txtCantidadDespacho.text= mate.cantidad_empaque.toString()
            binding.imageView.load(mate.material_empaque_imagen){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
            binding.btnDetalles.setOnClickListener {
                onItemClicked(mate, binding.btnDetalles)
            }
        }

    }
}