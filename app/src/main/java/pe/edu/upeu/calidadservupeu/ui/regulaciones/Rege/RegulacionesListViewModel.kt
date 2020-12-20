package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.CarritoRepository
import pe.edu.upeu.calidadservupeu.data.repository.RegulacionesRepository
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.Regulaciones

@ExperimentalCoroutinesApi
class RegulacionesListViewModel @ViewModelInject constructor(
    private val regulacionesRepository: RegulacionesRepository,
    private val carritoRepository: CarritoRepository) : ViewModel() {

    private  val _regulacionesLiveData=MutableLiveData<List<Regulaciones>>()
    val regulacionesLidaData:LiveData<List<Regulaciones>>
        get() =_regulacionesLiveData

    fun getPrimas(){
        viewModelScope.launch {
            regulacionesRepository.getRegulacionesPrima().collect{
                _regulacionesLiveData.value=it
            }
        }
    }
    fun getEmpaque(){
        viewModelScope.launch {
            regulacionesRepository.getRegulacionesEmpaque().collect{
                _regulacionesLiveData.value=it
            }
        }
    }
    fun getProductos(){
        viewModelScope.launch {
            regulacionesRepository.getRegulacionesProductos().collect{
                _regulacionesLiveData.value=it
            }
        }
    }
    fun createRegulacion(regulaciones: Regulaciones){
        viewModelScope.launch {
            regulacionesRepository.insertRegulacion(regulaciones)
        }
    }
    fun getCarrito(id:Int)=carritoRepository.getAllgetCarritoRegulacion(id).asLiveData()
    fun updateCarrito(carrito: Carrito,context:Context){
        viewModelScope.launch {
            val dato = carritoRepository.updateRegulacionById(carrito)
            if(dato.message() =="Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "La cantidad del producto deseado debe ser mayor a 0.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }
    fun deleteCarrito(id:Int){
        viewModelScope.launch {
            carritoRepository.deleteCarritoById(id)
        }
    }

    fun getRegulacion(id:Int)=regulacionesRepository.getRegulacionById(id).asLiveData()
}