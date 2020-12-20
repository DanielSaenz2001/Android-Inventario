package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

class CarritoViewHolder(
    private val binding: ItemProductoDespachoBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(carrito: Carrito, onItemClicked: (Carrito, ImageView, View)->Unit){
        if(UtilsToken.tipo_carrito == "Produccion"){

            binding.despachoName.text=""+carrito.materia_prima_nombre
            binding.txtCantidadDespacho.setText(carrito.cantidad_materias.toString())
            binding.textView13.text=""+carrito.materia_prima_stock
            binding.imageView.load(carrito.materia_prima_imagen){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }else{

            binding.despachoName.text=""+carrito.producto_nombre
            binding.txtCantidadDespacho.setText(carrito.cantidad_producto.toString())
            binding.textView13.text=""+carrito.productos_stock
            binding.imageView.load(carrito.producto_imagen){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }


        binding.btnDeleteDespacho.setOnClickListener {
            Log.i("LLEGA_P", "Delete:"+carrito.id)
            onItemClicked(carrito, binding.imageView, binding.btnDeleteDespacho)
        }
        binding.btnEditDespacho.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+carrito.id)
            onItemClicked(carrito, binding.imageView, binding.btnEditDespacho)
        }


    }
}