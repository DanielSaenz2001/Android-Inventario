package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Singleton
class CarritoRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
)  {
    @MainThread
    fun getAllCarritoDespachos(): Flow<List<Carrito>> = servicioProductoApi.getCarritoDespacho(
        UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun updateDespachoProductoById(carrito: Carrito)=servicioProductoApi.updateCarritoDespachoProductoById(UtilsToken.TOKEN_CONTENT,carrito.id!!,carrito)

    @MainThread
    suspend fun updateDespachoEmpaqueById(carrito: Carrito)=servicioProductoApi.updateCarritoDespachoEmpaqueById(UtilsToken.TOKEN_CONTENT,carrito.id!!,carrito)

    @MainThread
    suspend fun deleteCarritoById(carritoId: Int)=servicioProductoApi.deleteCarritoById(UtilsToken.TOKEN_CONTENT,carritoId)

    @MainThread
    suspend fun createCarritoDespacho(carrito: Carrito)=servicioProductoApi.createCarritoDespacho(UtilsToken.TOKEN_CONTENT,carrito)

    @MainThread
    fun getAllCarritoProduccion(): Flow<List<Carrito>> = servicioProductoApi.getCarritoProduccion(UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun createCarritoProduccion(carrito: Carrito)=servicioProductoApi.createCarritoProduccion(UtilsToken.TOKEN_CONTENT,carrito)

    @MainThread
    suspend fun updateProduccionById(carrito: Carrito)=servicioProductoApi.updateCarritoProduccionById(UtilsToken.TOKEN_CONTENT,carrito.id!!,carrito)

    @MainThread
    suspend fun createproductosDespachos(productDespachos: ProductDespachos)=
        servicioProductoApi.addProductosDespachos(UtilsToken.TOKEN_CONTENT,productDespachos)
    @MainThread
    suspend fun createproductosProduccion(produccion: Produccion)= servicioProductoApi.addProductosProduccion(UtilsToken.TOKEN_CONTENT,produccion)

    @MainThread
    fun getAllgetCarritoRegulacion(id:Int): Flow<Carrito> = servicioProductoApi.getCarritoRegulacion(UtilsToken.TOKEN_CONTENT,id)

    @MainThread
    suspend fun updateRegulacionById(carrito: Carrito)=servicioProductoApi.updateCarritoRegulacionById(UtilsToken.TOKEN_CONTENT,carrito.id!!,carrito)

    @MainThread
    suspend fun createCarritoRegulacion(carrito: Carrito)=servicioProductoApi.createCarritoRegulacion(UtilsToken.TOKEN_CONTENT,carrito)
    //
}