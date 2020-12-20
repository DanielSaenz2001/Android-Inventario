package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.viewholder


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemRegulacionesBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


class RegulacionesViewHolder(
    private val binding: ItemRegulacionesBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(regulaciones: Regulaciones, onItemClicked: (Regulaciones, imageView: ImageView, View)->Unit){
        binding.responsable.text=regulaciones.responsable
        binding.actividad.text= regulaciones.actividad
        binding.fecha.text=regulaciones.fecha
        binding.imageView.load(regulaciones.imagen_producto){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.imageView.setOnClickListener {
            onItemClicked(regulaciones, binding.imageView, binding.imageView)
        }
    }
}