package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemMaterialesEmbarqueBinding
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

class MaterialesEmpaqueViewHolder(
    private val binding: ItemMaterialesEmbarqueBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(materialesEmpaque: MaterialesEmpaque, onItemClicked: (MaterialesEmpaque, ImageView, View)->Unit){
        binding.embarqueNombre.text=materialesEmpaque.nombre
        binding.embarqueStock.text=""+materialesEmpaque.stock
        if(UtilsToken.tipo_actividad == "Crear_Despacho_Producto"|| UtilsToken.tipo_actividad =="Regulaciones"){
            binding.btnSelectEmbarque.visibility=View.VISIBLE
        }
        binding.imageView.load(materialesEmpaque.imagen_material_empaques){
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.imageView.setOnClickListener {
            Log.i("LLEGA_P", "Imagen:"+materialesEmpaque.id)
            onItemClicked(materialesEmpaque, binding.imageView, binding.imageView)
        }
        binding.btnSelectEmbarque.setOnClickListener {
            Log.i("LLEGA_P", "Delete:"+materialesEmpaque.id)
            onItemClicked(materialesEmpaque, binding.imageView, binding.btnSelectEmbarque)
        }
        binding.embarqueNombre.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+materialesEmpaque.id)
        }

    }
}