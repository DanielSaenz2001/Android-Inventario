package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.MaterialesEmpaqueRepository
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque

@ExperimentalCoroutinesApi
class MateriasEmpaqueDetailsViewModel @ViewModelInject constructor(
    private val materialesEmpaqueRepository: MaterialesEmpaqueRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
): ViewModel(){
    fun getEmpaque(id:Int)=materialesEmpaqueRepository.getEmpaqueById(id).asLiveData()

   fun updateEmpaque(materialesEmpaque: MaterialesEmpaque){
       viewModelScope.launch {
           materialesEmpaqueRepository.updateEmpaqueById(materialesEmpaque)
           getEmpaque(materialesEmpaque.id!!)
       }

   }

}