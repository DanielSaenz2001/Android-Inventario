package pe.edu.upeu.calidadservupeu.ui.Produccion.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoDetailsBinding
import pe.edu.upeu.calidadservupeu.model.ProduccionDetalles
import pe.edu.upeu.calidadservupeu.model.ProductosDespachosDetalles


class ProduccionDetailsViewHolder(
    private val binding: ItemProductoDespachoDetailsBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(produccion: ProduccionDetalles, onItemClicked: (ProduccionDetalles, View)->Unit){
        binding.despachoName.text=""+produccion.materia_prima_nombre
        binding.txtCantidadDespacho.setText(produccion.cantidad_materia.toString())
        binding.imageView.load(produccion.materia_prima_imagen){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.btnVerEmpaque.visibility= View.INVISIBLE
        /*binding.btnVerEmpaque.setOnClickListener {
            onItemClicked(produccion, binding.imageView, binding.btnVerEmpaque)
        }*/
    }
}