package pe.edu.upeu.calidadservupeu.ui.Produccion.details


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.ProduccionRepository
import pe.edu.upeu.calidadservupeu.model.*

@ExperimentalCoroutinesApi
class ProduccionDetailsViewModel @ViewModelInject constructor(
    private val produccionRepository: ProduccionRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    private  val _produccionLiveData= MutableLiveData<List<ProduccionDetalles>>()
    val produccionLidaData: LiveData<List<ProduccionDetalles>>
        get() =_produccionLiveData

    fun getProduccion(id:Int)=produccionRepository.getProduccionById(id).asLiveData()

    fun getProduccionDetalles(id:Int){
        viewModelScope.launch {
            produccionRepository.getProduccionDetallesById(id).collect{
                _produccionLiveData.value=it
            }
        }
    }
}