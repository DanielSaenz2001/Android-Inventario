package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.ProduccionDetalles
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProduccionRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
)  {
    fun getAllProduccion(): Flow<List<Produccion>> = servicioProductoApi.getAllProduccion(UtilsToken.TOKEN_CONTENT)

    @MainThread
    fun getProduccionById(ProduccionId:Int): Flow<Produccion> = servicioProductoApi.getProduccionById(UtilsToken.TOKEN_CONTENT,ProduccionId)

    @MainThread
    fun getProduccionDetallesById(ProduccionId:Int): Flow<List<ProduccionDetalles>> = servicioProductoApi.getProduccionDetailsById(UtilsToken.TOKEN_CONTENT,ProduccionId)


}