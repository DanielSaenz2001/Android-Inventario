package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoDespachoBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.viewholder.CarritoViewHolder

class CarritoListAdapter(
    private val onItemClicked: (Carrito, ImageView, View)->Unit
): ListAdapter<Carrito, CarritoViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder =
        CarritoViewHolder(
            ItemProductoDespachoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Carrito>() {
            override fun areItemsTheSame(
                oldItem: Carrito,
                newItem: Carrito
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Carrito,
                newItem: Carrito
            ): Boolean = oldItem == newItem
        }
    }
}