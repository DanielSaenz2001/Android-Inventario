package pe.edu.upeu.calidadservupeu.data.repository


import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.model.QR
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProductoRepository @Inject constructor(
    private val servicioProductoApi:ServiciosCalidadApi
){

    fun getAllProductos(): Flow<List<Producto>> = servicioProductoApi.getProductos(UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun insertProducto(producto: Producto) = servicioProductoApi.addProducto(UtilsToken.TOKEN_CONTENT,producto)

    @MainThread
    fun getProductoById(productoId:Int):Flow<Producto> = servicioProductoApi.getProductoById(UtilsToken.TOKEN_CONTENT,productoId)

    @MainThread
    fun getProductoById2(productoId:String)  = servicioProductoApi.getProductoById2(UtilsToken.TOKEN_CONTENT,productoId)

    @MainThread
    fun getAllProductosFiltro(producto: Producto): Flow<List<Producto>> = servicioProductoApi.getProductosFiltro(UtilsToken.TOKEN_CONTENT,producto)

    @MainThread
    suspend fun updateProductoById(producto: Producto)=servicioProductoApi.updateProductoId(UtilsToken.TOKEN_CONTENT,producto.id!!,producto)

    @MainThread
    suspend fun deleteProductoById(productoId: Int)=servicioProductoApi.deleteProductoId(UtilsToken.TOKEN_CONTENT,productoId)
}