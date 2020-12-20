package pe.edu.upeu.calidadservupeu.ui.qr


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import coil.api.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.data.repository.*
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.databinding.ActivityQrBinding
import pe.edu.upeu.calidadservupeu.model.*
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import java.lang.Exception

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class QrViewModel @ViewModelInject constructor(
     val usuariosRepository: UsuariosRepository,
     val productoRepository: ProductoRepository,
     val materiasPrimasRepository: MateriasPrimasRepository,
     val proveedoresRepository: ProveedoresRepository,
     val carritoRepository: CarritoRepository,
     val materialesEmpaqueRepository: MaterialesEmpaqueRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
      val _usuariosLiveData= MutableLiveData<List<QR>>()
     val usuariosLidaData: LiveData<List<QR>>
        get() =_usuariosLiveData


    private val _text = MutableLiveData<String>().apply {
        value = "Holas QR"
    }
    val text: LiveData<String> = _text


    fun usuarioById(id: String,binding: ActivityQrBinding,view:Int){
        viewModelScope.launch {
            usuariosRepository.getUserById2(id).collect {

                if (it.isEmpty()){
                    Log.i("datos prueba 2", "estoy vacio")
                }else{
                    _usuariosLiveData.value= it
                    Log.i("datos prueba 2", _usuariosLiveData.value!!.last().toString())

                    binding.DatosScanner.visibility =view
                    binding.textView11.text="Nombre:"
                    binding.textView12.text="Rol:"
                    binding.userNameQr.text = _usuariosLiveData.value!!.last().name
                    binding.userTipoQr.text = _usuariosLiveData.value!!.last().rol
                    binding.idQr.text = _usuariosLiveData.value!!.last().id.toString()


                    binding.imageViewQr.load(_usuariosLiveData.value!!.last().imagen_user.toString()){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                }
            }
        }
    }
    fun productoById(id: String)= productoRepository.getProductoById2(id).asLiveData()
    fun materiaById(id: String)= materiasPrimasRepository.getMateriaPrimaById2(id).asLiveData()


    fun empaqueById(id: String)=materialesEmpaqueRepository.getEmpaqueById2(id).asLiveData()
            //Log.i("DATA",data.observe(this))
/*Log.i("datos prueba 2", _usuariosLiveData.value!!.last().toString())

                        binding.DatosScanner.visibility =view
                    binding.textView11.text="Nombre:"
                    binding.textView12.text="Stock:"
                    binding.userNameQr.text = _usuariosLiveData.value!!.last().nombre
                    binding.userTipoQr.text = _usuariosLiveData.value!!.last().stock.toString()
                    binding.idQr.text = _usuariosLiveData.value!!.last().id.toString()



                    binding.imageViewQr.load(_usuariosLiveData.value!!.last().imagen_material_empaques.toString()){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }*/

        //}
    //

    fun createCarritoProduccion(carrito: Carrito, context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoProduccion(carrito)
            Log.i("datos de la creada", dato.message())
            if(dato.message()== "Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "No hay stock disponible en el almacen, comuniquele a su jefe lo mas antes posible.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message() == "Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "Ya existe ese producto en la lista.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }
    fun createCarritoRegulacion(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoRegulacion(carrito)
            Log.i("datos de la creada", dato.message())
        }
    }
    fun createCarritoDespacho(carrito: Carrito,context: Context){
        viewModelScope.launch {
            val dato = carritoRepository.createCarritoDespacho(carrito)
            Log.i("datos de la creada", dato.message())
            if(dato.message()== "Not Acceptable"){
                val toast: Toast = Toast.makeText(context,
                    "No hay stock disponible en el almacen, comuniquele a su jefe lo mas antes posible.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(dato.message() == "Bad Request"){
                val toast: Toast = Toast.makeText(context,
                    "Ya existe ese producto en la lista.",
                    Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
        }
    }
    fun updateCarritoDespacho(carrito: Carrito){
        Log.i("datos",carrito.toString())
        viewModelScope.launch {
            carritoRepository.updateDespachoEmpaqueById(carrito)
        }
    }
}
