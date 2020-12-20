package pe.edu.upeu.calidadservupeu.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.data.repository.UsuariosRepository

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(


    @Assisted private  val savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}