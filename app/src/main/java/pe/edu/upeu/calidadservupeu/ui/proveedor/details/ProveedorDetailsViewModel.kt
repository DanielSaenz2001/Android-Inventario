package pe.edu.upeu.calidadservupeu.ui.proveedor.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.ProveedoresRepository
import pe.edu.upeu.calidadservupeu.model.Proveedores

@ExperimentalCoroutinesApi
class ProveedorDetailsViewModel @ViewModelInject constructor(
    private val proveedorRepository: ProveedoresRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
): ViewModel(){
    fun getProveedor(id:Int)=proveedorRepository.getProveedorById(id).asLiveData()

   fun updateProveedor(proveedor: Proveedores){
       viewModelScope.launch {
           proveedorRepository.updateProveedorId(proveedor)
           getProveedor(proveedor.id!!)
       }

   }

}