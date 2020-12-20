package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario



import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.ControlRepository
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ControlInventarioCreateViewModel @ViewModelInject constructor(
    private val controlRepository: ControlRepository) : ViewModel() {

    private  val _controlLiveData= MutableLiveData<List<ControlInventarioDetalles>>()
    val controlLidaData: LiveData<List<ControlInventarioDetalles>>
        get() =_controlLiveData

    fun getInventarioList(id:Int){
        viewModelScope.launch {
            controlRepository.getControlInventarioDetalles(id).collect{
                _controlLiveData.value=it
            }
        }
    }
    fun update(id:Int,controlInventarioDetalles: ControlInventarioDetalles){
        viewModelScope.launch {
            controlRepository.updateControlInventarioDetallesUpdateById(id,controlInventarioDetalles)
        }
    }

    fun finControl(id:Int, context: Context){
        viewModelScope.launch {
            val dato=controlRepository.controlFinish(id)
            Log.i("DATO",dato.message())
            if(dato.message()=="Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "Todovia tiene que revisar algunos productos.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message()=="Payment Required"){
                val toast: Toast = Toast.makeText(context,
                    "El Inventario ya fue terminado :D",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message()=="OK"){
                if(UtilsToken.inventario=="prima"){
                    val intent = Intent(context, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"prima")
                    context.startActivity(intent)
                }
                if(UtilsToken.inventario=="empaque"){
                    val intent = Intent(context, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"empaque")
                    context.startActivity(intent)
                }
                if(UtilsToken.inventario=="producto"){
                    val intent = Intent(context, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"producto")
                    context.startActivity(intent)
                }
            }
        }
    }
}