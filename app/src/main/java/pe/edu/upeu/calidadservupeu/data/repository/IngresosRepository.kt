package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.IntMate
import pe.edu.upeu.calidadservupeu.model.IntMateDetalles
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class IngresosRepository  @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
)  {
    @MainThread
    fun getIngresoMateriasPrimas(): Flow<List<IntMate>> = servicioProductoApi.getIngresoMateriasPrimas(
        UtilsToken.TOKEN_CONTENT)

    @MainThread
    fun getIngresoMaterialesPrimasById(id: Int)=servicioProductoApi.getIngresoMateriasPrimasById(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    fun getIngresoMaterialesEmpaque(): Flow<List<IntMate>> = servicioProductoApi.getIngresoMaterialesEmpaque(UtilsToken.TOKEN_CONTENT)

    @MainThread
    fun getIngresoMaterialesEmpaqueById(id: Int)=servicioProductoApi.getIngresoMaterialesEmpaqueById(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    fun getIngresoPrimaDetallesById(id:Int):Flow<List<IntMateDetalles>> = servicioProductoApi.getIngresoPrimaDetallesById(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    fun getIngresoEmpaqueDetallesById(id:Int):Flow<List<IntMateDetalles>> = servicioProductoApi.getIngresoEmpaqueDetallesById(UtilsToken.TOKEN_CONTENT,id)
}