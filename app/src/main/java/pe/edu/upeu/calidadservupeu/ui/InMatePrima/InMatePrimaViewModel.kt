package pe.edu.upeu.calidadservupeu.ui.InMatePrima


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.IngresosRepository
import pe.edu.upeu.calidadservupeu.model.*

@ExperimentalCoroutinesApi
class InMatePrimaViewModel @ViewModelInject constructor(
    private val ingresosRepository: IngresosRepository) : ViewModel() {

    private  val _ingresosLiveData= MutableLiveData<List<IntMate>>()
    val ingresosLidaData: LiveData<List<IntMate>>
        get() =_ingresosLiveData

    fun getIngresos(){
        viewModelScope.launch {
            ingresosRepository.getIngresoMateriasPrimas().collect{
                _ingresosLiveData.value=it
            }
        }
    }
}