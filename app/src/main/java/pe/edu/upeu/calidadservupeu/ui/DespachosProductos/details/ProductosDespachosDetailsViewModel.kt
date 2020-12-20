package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.details

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
class ProductosDespachosDetailsViewModel @ViewModelInject constructor(
    private val despachosRepository: DespachosRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    private  val _despachosLiveData= MutableLiveData<List<ProductosDespachosDetalles>>()
    val despachosLidaData: LiveData<List<ProductosDespachosDetalles>>
        get() =_despachosLiveData


    fun getDespachos(id:Int)=despachosRepository.getDespachosById(id).asLiveData()

    fun getDespachosDetalles(id:Int){
        viewModelScope.launch {
            despachosRepository.getDespachosDetallesById(id).collect{
                _despachosLiveData.value=it
                Log.i("DATOS:", _despachosLiveData.value.toString())
        } }
    }
}