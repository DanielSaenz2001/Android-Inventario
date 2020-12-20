package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upeu.calidadservupeu.databinding.ItemDespachoBinding
import pe.edu.upeu.calidadservupeu.model.ProductDespachos


class DespachosViewHolder(
    private val binding: ItemDespachoBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(despacho: ProductDespachos, onItemClicked: (ProductDespachos, View)->Unit){
        binding.despachoConductor.text=despacho.nombreConductor
        binding.txtVehiculoDespacho.text= despacho.vehiculo
        binding.txtFechaDespacho.text=despacho.fecha


        binding.btnVerDespacho.setOnClickListener {
            Log.i("LLEGA_U", "Editar"+despacho.id)
            onItemClicked(despacho, binding.btnVerDespacho)
        }


    }
}