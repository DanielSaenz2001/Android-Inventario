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
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ControlInventarioViewModel @ViewModelInject constructor(
    private val controlRepository: ControlRepository) : ViewModel() {

    private  val _inventarioLiveData=MutableLiveData<List<ControlInventario>>()
    val inventarioLidaData:LiveData<List<ControlInventario>>
        get() =_inventarioLiveData

    fun getPrimas(){
        viewModelScope.launch {
            controlRepository.getControlPrimas().collect{
                _inventarioLiveData.value=it
            }
        }
    }
    fun getEmpaque(){
        viewModelScope.launch {
            controlRepository.getControlEmpaque().collect{
                _inventarioLiveData.value=it
            }
        }
    }
    fun getProductos(){
        viewModelScope.launch {
            controlRepository.getControlProducto().collect{
                _inventarioLiveData.value=it
            }
        }
    }

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
    fun addProductos(controlInventario: ControlInventario, context: Context){
        viewModelScope.launch {
           val data= controlRepository.addControlProducto(controlInventario)
            Log.i("Mensaje",data.message())
            Log.i("Mensaje",data.body().toString())
            if(data.message()=="Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "Hay una control de inventario existente en este rubro porfavor llame al responsable que lo termine o cierre.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }else if(data.message()=="Partial Content"){
                val id=data.body()?.id
                Log.i("DATAID",id.toString())
                val intent = Intent(context, ControlInventarioCreate::class.java)
                intent.putExtra(ControlInventarioCreate.INVENTARIO_ID,id)
                context.startActivity(intent)
            }else if(data.message()=="OK"){
                val id=data.body()?.id
                val toast: Toast = Toast.makeText(context,
                    "Control creado.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                val intent = Intent(context, ControlInventarioCreate::class.java)
                intent.putExtra(ControlInventarioCreate.INVENTARIO_ID,id)
                context.startActivity(intent)
            }
        }
    }
    fun addPrima(controlInventario: ControlInventario,context: Context){
        viewModelScope.launch {
            val data= controlRepository.addControlPrima(controlInventario)
            if(data.message()=="Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "Hay una control de inventario existente en este rubro porfavor llame al responsable que lo termine o cierre.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }else if(data.message()=="Partial Content"){

                val id=data.body()?.id
                Log.i("DATAID",id.toString())
                val intent = Intent(context, ControlInventarioCreate::class.java)
                intent.putExtra(ControlInventarioCreate.INVENTARIO_ID,id)
                context.startActivity(intent)
            }else if(data.message()=="OK"){
                val id=data.body()?.id
                val toast: Toast = Toast.makeText(context,
                    "Control creado.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                val intent = Intent(context, ControlInventarioCreate::class.java)
                intent.putExtra(ControlInventarioCreate.INVENTARIO_ID,id)
                context.startActivity(intent)
            }
        }
    }
    fun addEmpaque(controlInventario: ControlInventario,context: Context){
        viewModelScope.launch {
            val data= controlRepository.addControlEmpaque(controlInventario)
            Log.i("Mensaje",data.message())
            Log.i("Mensaje",data.body().toString())
            if(data.message()=="Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "Hay una control de inventario existente en este rubro porfavor llame al responsable que lo termine o cierre.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }else if(data.message()=="Partial Content"){
                val id=data.body()?.id
                Log.i("DATAID",id.toString())
                val intent = Intent(context, ControlInventarioCreate::class.java)
                intent.putExtra(ControlInventarioCreate.INVENTARIO_ID,id)
                context.startActivity(intent)
            }else if(data.message()=="OK"){
                val id=data.body()?.id
                val toast: Toast = Toast.makeText(context,
                    "Control creado.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }
    fun getControl(id:Int)=controlRepository.getControlInventarioById(id).asLiveData()
}