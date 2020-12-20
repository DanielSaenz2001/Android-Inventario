package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque

import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.CarritoRepository
import pe.edu.upeu.calidadservupeu.data.repository.MaterialesEmpaqueRepository
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.activity.MaterialEmpaques

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MaterialesEmpaqueViewModel @ViewModelInject constructor(
    private val materialesEmpaqueRepository: MaterialesEmpaqueRepository,
    private val carritoRepository: CarritoRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private  val _empaqueLiveData= MutableLiveData<List<MaterialesEmpaque>>()
    val empaqueLidaData: LiveData<List<MaterialesEmpaque>>
        get() =_empaqueLiveData

    fun getEmpaque(){
        viewModelScope.launch {
            materialesEmpaqueRepository.getAllEmpaques().collect{
                _empaqueLiveData.value=it
            }
        }
    }
    fun deleteEmpaqueById(materialesEmpaque: MaterialesEmpaque){
        viewModelScope.launch {
            materialesEmpaqueRepository.deleteEmpaqueById(materialesEmpaque.id!!)
            materialesEmpaqueRepository.getAllEmpaques().collect{
                _empaqueLiveData.value=it
            }
        }
    }

    fun createEmpaque(materialesEmpaque: MaterialesEmpaque){
        viewModelScope.launch {
            materialesEmpaqueRepository.insertEmpaque(materialesEmpaque)
            materialesEmpaqueRepository.getAllEmpaques().collect{
                _empaqueLiveData.value=it
            }
        }
    }
    fun getEmpaquesFiltro(materialesEmpaque: MaterialesEmpaque){
        viewModelScope.launch {
            materialesEmpaqueRepository.getAllEmpaquesFiltro(materialesEmpaque).collect {
                _empaqueLiveData.value=it
            }
        }
    }
    fun updateCarritoDespacho(carrito: Carrito){
        Log.i("datos",carrito.toString())
        viewModelScope.launch {
            carritoRepository.updateDespachoEmpaqueById(carrito)
        }
    }

    fun createCarritoRegulacion(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoRegulacion(carrito)
            Log.i("datos de la creada", dato.message())
        }
    }
}