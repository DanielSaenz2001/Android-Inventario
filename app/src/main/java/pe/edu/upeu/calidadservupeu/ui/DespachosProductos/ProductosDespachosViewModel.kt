package pe.edu.upeu.calidadservupeu.ui.DespachosProductos

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.DespachosRepository
import pe.edu.upeu.calidadservupeu.data.repository.LoginRepository
import pe.edu.upeu.calidadservupeu.model.*
import pe.edu.upeu.calidadservupeu.ui.login.LoginActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
class ProductosDespachosViewModel @ViewModelInject constructor(
    private val despachosRepository: DespachosRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    private  val _despachosLiveData= MutableLiveData<List<ProductDespachos>>()
    val despachosLidaData: LiveData<List<ProductDespachos>>
        get() =_despachosLiveData

    fun getDespachos(){
        viewModelScope.launch {
            despachosRepository.getAllDespachos().collect{
                _despachosLiveData.value=it
            } }
    }
    fun getDespachosFiltro(proveedor: Proveedores){
        viewModelScope.launch {
            /*proveedorRepository.getProveedoresFiltro(proveedor).collect {
                _proveedorLiveData.value=it
                Log.i("datos del filtro",_proveedorLiveData.value.toString())
            }*/
        }
    }
}