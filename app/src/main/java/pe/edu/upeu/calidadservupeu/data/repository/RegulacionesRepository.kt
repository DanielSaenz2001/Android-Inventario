package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.model.Proveedores
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class RegulacionesRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
){
    fun getRegulacionesPrima(): Flow<List<Regulaciones>> = servicioProductoApi.getRegulacionesPrimas(
        UtilsToken.TOKEN_CONTENT)

    fun getRegulacionesEmpaque(): Flow<List<Regulaciones>> = servicioProductoApi.getRegulacionesEmpaque(UtilsToken.TOKEN_CONTENT)

    fun getRegulacionesProductos(): Flow<List<Regulaciones>> = servicioProductoApi.getRegulacionesProducto(UtilsToken.TOKEN_CONTENT)

    @MainThread
    fun getRegulacionById(id:Int):Flow<Regulaciones> = servicioProductoApi.getRegulacionesById(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    suspend fun insertRegulacion(regulaciones: Regulaciones) = servicioProductoApi.addRegulacion(UtilsToken.TOKEN_CONTENT,regulaciones)
}