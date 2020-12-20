package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.model.ProductosDespachosDetalles
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class DespachosRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
)  {

    fun getAllDespachos(): Flow<List<ProductDespachos>> = servicioProductoApi.getAllDespacho(
        UtilsToken.TOKEN_CONTENT)

    @MainThread
    fun getDespachosById(DespachoId:Int):Flow<ProductDespachos> = servicioProductoApi.getDespachoById(UtilsToken.TOKEN_CONTENT,DespachoId)

    @MainThread
    fun getDespachosDetallesById(DespachoId:Int):Flow<List<ProductosDespachosDetalles>> = servicioProductoApi.getDespachoDetailsById(UtilsToken.TOKEN_CONTENT,DespachoId)
}