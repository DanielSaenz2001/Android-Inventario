package pe.edu.upeu.calidadservupeu.ui.producto

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.hilt.Assisted
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.CarritoRepository
import pe.edu.upeu.calidadservupeu.data.repository.ProductoRepository
import pe.edu.upeu.calidadservupeu.data.repository.UsuariosRepository
import pe.edu.upeu.calidadservupeu.databinding.ItemProductoBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.model.State
import pe.edu.upeu.calidadservupeu.model.User

@ExperimentalCoroutinesApi
class ProductoViewModel @ViewModelInject constructor(
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private  val _productosLiveData=MutableLiveData<List<Producto>>()
    val productosLidaData:LiveData<List<Producto>>
        get() =_productosLiveData

    fun getProductos(){
        viewModelScope.launch {
            productoRepository.getAllProductos().collect{
            _productosLiveData.value=it
                //binding.btnDelete.visibility = View.VISIBLE
        } }
    }
    fun deleteProductById(producto: Producto){
        viewModelScope.launch {
           productoRepository.deleteProductoById(producto.id!!)
           productoRepository.getAllProductos().collect{
                _productosLiveData.value=it

           }
        }
    }

    fun createProduct(producto: Producto){
        viewModelScope.launch {
            productoRepository.insertProducto(producto)
            productoRepository.getAllProductos().collect{
                _productosLiveData.value=it
            }
        }
    }
    fun getProductosFiltro(producto: Producto){
        viewModelScope.launch {
            productoRepository.getAllProductosFiltro(producto).collect {
                _productosLiveData.value=it
                Log.i("datos del filtro",_productosLiveData.value.toString())
            }
        }
    }
    fun createCarritoDespacho(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoDespacho(carrito)
            Log.i("datos de la creada", dato.message())
            if(dato.message()== "Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "No hay stock disponible en el almacen, comuniquele a su jefe lo mas antes posible.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message() == "Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "Ya existe ese producto en la lista.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }
    fun createCarritoRegulacion(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoRegulacion(carrito)
            Log.i("datos de la creada", dato.message())
        }
    }
}