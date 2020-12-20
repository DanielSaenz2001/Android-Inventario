package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Login
import pe.edu.upeu.calidadservupeu.model.Register
import pe.edu.upeu.calidadservupeu.model.User
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class LoginRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi

) {
    @MainThread
    suspend fun loginUser(login: Login)=servicioProductoApi.loginUser(login)
    @MainThread
    suspend fun getProfile(token:String)=servicioProductoApi.getProfile(token)
    @MainThread
    suspend fun cambiarContra(token:String, user: User)=servicioProductoApi.cambiarContra(token,user)
    @MainThread
    suspend fun cambiarNombre(token:String, user: User)=servicioProductoApi.cambiarNombre(token,user)
    @MainThread
    suspend fun cambiarImagen(token:String, user: User)=servicioProductoApi.cambiarImagenUser(token,user)
    @MainThread
    suspend fun logoutUser(token:String)=servicioProductoApi.logout(token)
}