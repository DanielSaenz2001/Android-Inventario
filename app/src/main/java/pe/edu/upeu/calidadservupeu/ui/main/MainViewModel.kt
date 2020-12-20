package pe.edu.upeu.calidadservupeu.ui.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.data.repository.LoginRepository
import pe.edu.upeu.calidadservupeu.model.Login
import pe.edu.upeu.calidadservupeu.model.Register
import pe.edu.upeu.calidadservupeu.model.User
import pe.edu.upeu.calidadservupeu.model.credenciales
import pe.edu.upeu.calidadservupeu.ui.login.LoginActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    @Assisted private val savedStateHandle: SavedStateHandle ) : ViewModel() {

    fun getProfile(token:String){
        viewModelScope.launch {
            val gola = loginRepository.getProfile(token).body()
            UtilsToken.email_user=gola?.email.toString()
            UtilsToken.name_user=gola?.name.toString()
            UtilsToken.rol_user=gola?.rol.toString()
        }
    }
    fun logout(token:String,context: Context){
        viewModelScope.launch {
            loginRepository.logoutUser(token)
            UtilsToken.TOKEN_CONTENT=""
            UtilsToken.email_user=""
            UtilsToken.name_user=""
            UtilsToken.rol_user=""
            val toast: Toast = Toast.makeText(context, "Vuelva pronto", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
    fun cambiarContra(token:String,user: User,context: Context){
        viewModelScope.launch {
            val dx = loginRepository.cambiarContra(token, user).code()
            if(dx.toString() == "401"){
                val toast: Toast = Toast.makeText(context, "Email o contraseña antigua estan equivocadas", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
            if(dx.toString() == "200"){
                val toast: Toast = Toast.makeText(context, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
            if(dx.toString() == "300"){
                val toast: Toast = Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
        }
    }
    fun cambiarNombre(token:String,user: User,context: Context){
        viewModelScope.launch {
            loginRepository.cambiarNombre(token, user).code()
            UtilsToken.name_user=user?.name.toString()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
    fun cambiarImagen(token:String,user: User,context: Context){
        viewModelScope.launch {
            loginRepository.cambiarImagen(token, user).code()
            UtilsToken.imagen_user=user?.imagen_user.toString()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}