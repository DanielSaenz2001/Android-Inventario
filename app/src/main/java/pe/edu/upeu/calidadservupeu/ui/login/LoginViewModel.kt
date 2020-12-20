package pe.edu.upeu.calidadservupeu.ui.login


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.data.repository.LoginRepository
import pe.edu.upeu.calidadservupeu.model.Login
import pe.edu.upeu.calidadservupeu.model.Register
import pe.edu.upeu.calidadservupeu.model.credenciales
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {



    var hola:credenciales?? = null
    fun loginUser(login: Login, context: Context){
        viewModelScope.launch {
            hola =  loginRepository.loginUser(login).body()
            UtilsToken.TOKEN_CONTENT="bearer " +hola?.access_token.toString()
            if(hola == null){
                val toast: Toast = Toast.makeText(context, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }else{
                getProfile(UtilsToken.TOKEN_CONTENT,context)

            }
        }
    }
    fun getProfile(token:String, context: Context){
        viewModelScope.launch {
            val gola = loginRepository.getProfile(token).body()
            if(gola?.autorizado == 1){
                UtilsToken.email_user=gola?.email.toString()
                UtilsToken.name_user=gola?.name.toString()
                UtilsToken.rol_user=gola?.rol.toString()
                UtilsToken.area_user=gola?.area.toString()
                UtilsToken.imagen_user=gola?.imagen_user.toString()
                context.startActivity(Intent(context, MainActivity::class.java))
            }else{
                val toast: Toast = Toast.makeText(context, "Su usuario no esta autorizado para entrar a esta aplicación", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
        }
    }
}
