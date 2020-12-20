package pe.edu.upeu.calidadservupeu.ui.InMatePrima.details


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.IngresosRepository
import pe.edu.upeu.calidadservupeu.model.IntMateDetalles

@ExperimentalCoroutinesApi
class InMatePrimaDetailsViewModel @ViewModelInject constructor(
    private val ingresosRepository: IngresosRepository) : ViewModel() {
    fun getIngresos(id:Int)=ingresosRepository.getIngresoMaterialesPrimasById(id).asLiveData()


    private  val _ingresosLiveData= MutableLiveData<List<IntMateDetalles>>()
    val ingresosLidaData: LiveData<List<IntMateDetalles>>
        get() =_ingresosLiveData

    fun getDetalles(id:Int){
        viewModelScope.launch {
            ingresosRepository.getIngresoPrimaDetallesById(id).collect{
                _ingresosLiveData.value=it
            }
        }
    }
}