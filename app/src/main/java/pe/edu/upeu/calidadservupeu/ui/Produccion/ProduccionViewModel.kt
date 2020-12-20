package pe.edu.upeu.calidadservupeu.ui.Produccion

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
import pe.edu.upeu.calidadservupeu.data.repository.ProduccionRepository
import pe.edu.upeu.calidadservupeu.model.*
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.ui.login.LoginActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
class ProduccionViewModel @ViewModelInject constructor(
    private val produccionRepository: ProduccionRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    private  val _produccionLiveData= MutableLiveData<List<Produccion>>()
    val produccionLidaData: LiveData<List<Produccion>>
        get() =_produccionLiveData

    fun getProduccion(){
        viewModelScope.launch {
            produccionRepository.getAllProduccion().collect{
                _produccionLiveData.value=it
            }
        }
    }
}