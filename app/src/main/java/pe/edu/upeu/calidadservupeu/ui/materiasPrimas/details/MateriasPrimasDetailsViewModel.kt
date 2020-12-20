package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.MateriasPrimasRepository
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas

@ExperimentalCoroutinesApi
class MateriasPrimasDetailsViewModel @ViewModelInject constructor(
    private val materiasPrimasRepository: MateriasPrimasRepository,
    @Assisted private  val savedStateHandle: SavedStateHandle
): ViewModel(){
    fun getMaterias(id:Int)=materiasPrimasRepository.getMateriaPrimaById(id).asLiveData()

   fun updateMateria(materiasPrimas: MateriasPrimas){
       viewModelScope.launch {
           materiasPrimasRepository.updateMateriaPrimaById(materiasPrimas)
           getMaterias(materiasPrimas.id!!)
       }

   }

}