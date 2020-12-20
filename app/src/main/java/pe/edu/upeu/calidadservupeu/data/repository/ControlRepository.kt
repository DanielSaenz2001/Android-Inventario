package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ControlRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi){

    fun getControlPrimas(): Flow<List<ControlInventario>> = servicioProductoApi.getControlPrimas(
        UtilsToken.TOKEN_CONTENT)

    fun getControlEmpaque(): Flow<List<ControlInventario>> = servicioProductoApi.getControlEmpaque(UtilsToken.TOKEN_CONTENT)

    fun getControlProducto(): Flow<List<ControlInventario>> = servicioProductoApi.getControlProducto(UtilsToken.TOKEN_CONTENT)

    fun getControlInventarioDetalles(id:Int): Flow<List<ControlInventarioDetalles>> = servicioProductoApi.getControlInventarioDetalles(UtilsToken.TOKEN_CONTENT,id)
//
    @MainThread
    suspend fun updateControlInventarioDetallesUpdateById(id:Int,controlInventario: ControlInventarioDetalles)= servicioProductoApi.getControlInventarioDetallesUpdateById(UtilsToken.TOKEN_CONTENT,id,controlInventario)

    @MainThread
    fun getControlInventarioById(id:Int):Flow<ControlInventario> = servicioProductoApi.getControlInventarioById(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    suspend fun addControlPrima(controlInventario: ControlInventario)= servicioProductoApi.addControlPrima(UtilsToken.TOKEN_CONTENT,controlInventario)

    @MainThread
    suspend fun addControlEmpaque(controlInventario: ControlInventario)= servicioProductoApi.addControlEmpaque(UtilsToken.TOKEN_CONTENT,controlInventario)

    @MainThread
    suspend fun addControlProducto(controlInventario: ControlInventario)= servicioProductoApi.addControlProducto(UtilsToken.TOKEN_CONTENT,controlInventario)

    @MainThread
    suspend fun controlFinish(id:Int) = servicioProductoApi.controlFinish(UtilsToken.TOKEN_CONTENT,id)

    //
}