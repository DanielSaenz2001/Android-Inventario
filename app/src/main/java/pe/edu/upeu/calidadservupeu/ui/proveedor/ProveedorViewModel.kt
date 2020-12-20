package pe.edu.upeu.calidadservupeu.ui.proveedor

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.ProveedoresRepository
import pe.edu.upeu.calidadservupeu.model.Proveedores

@ExperimentalCoroutinesApi
class ProveedorViewModel @ViewModelInject constructor(
    private val proveedorRepository: ProveedoresRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private  val _proveedorLiveData=MutableLiveData<List<Proveedores>>()
    val proveedoresLidaData:LiveData<List<Proveedores>>
        get() =_proveedorLiveData

    fun getProveedores(){
        viewModelScope.launch {
            proveedorRepository.getAllProveedores().collect{
                _proveedorLiveData.value=it
            } }
    }
    fun deleteProveedorById(proveedor: Proveedores){
        viewModelScope.launch {
            proveedorRepository.deleteProveedorId(proveedor.id!!)
            proveedorRepository.getAllProveedores().collect{
                _proveedorLiveData.value=it
            }
        }
    }

    fun createProveedor(proveedor: Proveedores){
        viewModelScope.launch {
            proveedorRepository.insertProveedor(proveedor)
            proveedorRepository.getAllProveedores().collect{
                _proveedorLiveData.value=it
            }
        }
    }
    fun getProveedoresFiltro(proveedor: Proveedores){
        viewModelScope.launch {
            proveedorRepository.getProveedoresFiltro(proveedor).collect {
                _proveedorLiveData.value=it
                Log.i("datos del filtro",_proveedorLiveData.value.toString())
            }
        }
    }

}