package pe.edu.upeu.calidadservupeu.ui.materiasPrimas

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
import pe.edu.upeu.calidadservupeu.data.repository.MateriasPrimasRepository
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas

@ExperimentalCoroutinesApi
class MateriasPrimasViewModel @ViewModelInject constructor(
    private val materiasPrimasRepository: MateriasPrimasRepository,
    private val carritoRepository: CarritoRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private  val _materiasLiveData= MutableLiveData<List<MateriasPrimas>>()
    val materiasLidaData: LiveData<List<MateriasPrimas>>
        get() =_materiasLiveData

    fun getMaterias(){
        viewModelScope.launch {
            materiasPrimasRepository.getAllMateriasPrimas().collect{
                _materiasLiveData.value=it
            } }
    }
    fun deleteMateriaById(materiasPrimas: MateriasPrimas){
        viewModelScope.launch {
            materiasPrimasRepository.deleteMateriaPrimaById(materiasPrimas.id!!)
            materiasPrimasRepository.getAllMateriasPrimas().collect{
                _materiasLiveData.value=it
            }
        }
    }

    fun createMateria(materiasPrimas: MateriasPrimas){
        viewModelScope.launch {
            materiasPrimasRepository.insertMateriaPrima(materiasPrimas)
            materiasPrimasRepository.getAllMateriasPrimas().collect{
                _materiasLiveData.value=it
            }
        }
    }
    fun getMateriasFiltro(materiasPrimas: MateriasPrimas){
        viewModelScope.launch {
            materiasPrimasRepository.getAllMateriasPrimasFiltro(materiasPrimas).collect {
                _materiasLiveData.value=it
                Log.i("datos del filtro",_materiasLiveData.value.toString())
            }
        }
    }

    fun createCarritoProduccion(carrito: Carrito, context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoProduccion(carrito)
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