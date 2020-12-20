package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemMateriasPrimasBinding
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

class MateriasPrimasViewHolder(
    private val binding: ItemMateriasPrimasBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(materiasPrimas: MateriasPrimas, onItemClicked: (MateriasPrimas, ImageView, View)->Unit){
        binding.materiasPrimasNombre.text=materiasPrimas.nombre
        binding.materiasPrimasStock.text=""+materiasPrimas.stock
        if(UtilsToken.tipo_actividad == "Crear_Despacho_Producto" || UtilsToken.tipo_actividad == "Crear_Produccion"|| UtilsToken.tipo_actividad =="Regulaciones"){
            binding.btnSelectMateriasPrimas.visibility=View.VISIBLE
        }
        binding.imageView.load(materiasPrimas.imagen_materias_primas){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.imageView.setOnClickListener {
            Log.i("LLEGA_P", "Imagen:"+materiasPrimas.id)
            onItemClicked(materiasPrimas, binding.imageView, binding.imageView)
        }
        binding.btnSelectMateriasPrimas.setOnClickListener {
            Log.i("LLEGA_P", "Delete:"+materiasPrimas.id)
            onItemClicked(materiasPrimas, binding.imageView, binding.btnSelectMateriasPrimas)
        }
        binding.materiasPrimasNombre.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+materiasPrimas.id)
        }

    }
}