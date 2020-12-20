package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create


import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.CarritoRepository
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.ProductDespachos

@ExperimentalCoroutinesApi
class ProductosDespachosCreateViewModel @ViewModelInject constructor(
    private val carritoRepository: CarritoRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    private  val _carritoLiveData= MutableLiveData<List<Carrito>>()
    val carritoLidaData: LiveData<List<Carrito>>
        get() =_carritoLiveData

    fun getCarritoDespacho(){
        viewModelScope.launch {
            carritoRepository.getAllCarritoDespachos().collect{
                _carritoLiveData.value=it
            } }
    }
    fun updateCarritoDespacho(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato =carritoRepository.updateDespachoProductoById(carrito)

            Log.i("datos de la creada", dato.message())
            if(dato.message() == "Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "La cantidad del producto no puede ser 0, le aconsejamos que lo elimine.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message() == "Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "La cantidad del producto deseada es mayor a la de stock, le aconsejamos que lo verifique",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            getCarritoDespacho()
        }
    }
    fun updateCarritoDespacho2(carrito: Carrito){
        viewModelScope.launch {
            carritoRepository.updateDespachoEmpaqueById(carrito)
            getCarritoDespacho()
        }
    }
    fun deleteCarrito(carrito: Carrito){
        viewModelScope.launch {
            carritoRepository.deleteCarritoById(carrito.id!!)
            getCarritoDespacho()
        }
    }
    fun createDespacho(productDespachos: ProductDespachos,context: Context){
        viewModelScope.launch {
            val dato =carritoRepository.createproductosDespachos(productDespachos)
            getCarritoDespacho()
            if(dato.message() == "Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "No se puedo crear ya que no cuenta con ningun producto.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }

}