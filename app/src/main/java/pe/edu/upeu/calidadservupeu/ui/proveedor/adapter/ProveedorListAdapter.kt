package pe.edu.upeu.calidadservupeu.ui.proveedor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoBinding
import pe.edu.upeu.calidadservupeu.databinding.ItemProveedorBinding
import pe.edu.upeu.calidadservupeu.model.Proveedores
import pe.edu.upeu.calidadservupeu.ui.proveedor.viewholder.ProveedorViewHolder

class ProveedorListAdapter(
    private val onItemClicked: (Proveedores, ImageView, View)->Unit
): ListAdapter<Proveedores, ProveedorViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorViewHolder =
        ProveedorViewHolder(
            ItemProveedorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: ProveedorViewHolder, position: Int) =holder.bind(getItem(position), onItemClicked)

    companion object{
        private val DIFF_CALLBACK=object : DiffUtil.ItemCallback<Proveedores>(){
            override fun areItemsTheSame(oldItem: Proveedores, newItem: Proveedores): Boolean = oldItem.id==newItem.id
            override fun areContentsTheSame(oldItem: Proveedores, newItem: Proveedores): Boolean = oldItem==newItem
        }
    }
}