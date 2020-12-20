package pe.edu.upeu.calidadservupeu.data.repository


import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.QR
import pe.edu.upeu.calidadservupeu.model.User
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Singleton
class UsuariosRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
) {


    @MainThread
    fun getUserById2(userId:String):Flow<List<QR>> = servicioProductoApi.getUserById2(UtilsToken.TOKEN_CONTENT,userId)



}