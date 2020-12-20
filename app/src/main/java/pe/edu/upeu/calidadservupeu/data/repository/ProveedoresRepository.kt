package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Proveedores
import pe.edu.upeu.calidadservupeu.model.QR
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProveedoresRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
) {
    fun getAllProveedores(): Flow<List<Proveedores>> = servicioProductoApi.getProveedores(UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun insertProveedor(proveedores: Proveedores) = servicioProductoApi.addProveedores(UtilsToken.TOKEN_CONTENT,proveedores)

    @MainThread
    fun getProveedorById(proveedorId:Int):Flow<Proveedores> = servicioProductoApi.getProveedoresById(UtilsToken.TOKEN_CONTENT,proveedorId)

    @MainThread
    fun getProveedorById2(proveedorId:String):Flow<List<QR>> = servicioProductoApi.getProveedoresById2(UtilsToken.TOKEN_CONTENT,proveedorId)

    @MainThread
    fun getProveedoresFiltro(proveedores: Proveedores): Flow<List<Proveedores>> = servicioProductoApi.getProveedoresFiltro(UtilsToken.TOKEN_CONTENT,proveedores)

    @MainThread
    suspend fun updateProveedorId(proveedores: Proveedores)=servicioProductoApi.updateProveedoresId(UtilsToken.TOKEN_CONTENT,proveedores.id!!,proveedores)

    @MainThread
    suspend fun deleteProveedorId(proveedorId: Int)=servicioProductoApi.deleteProveedoresId(UtilsToken.TOKEN_CONTENT,proveedorId)
}