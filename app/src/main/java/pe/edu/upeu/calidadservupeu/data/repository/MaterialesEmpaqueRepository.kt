package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.model.QR
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response

@ExperimentalCoroutinesApi
@Singleton
class MaterialesEmpaqueRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
) {
    fun getAllEmpaques(): Flow<List<MaterialesEmpaque>> = servicioProductoApi.getEmpaques(UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun insertEmpaque(materialesEmpaque: MaterialesEmpaque) = servicioProductoApi.addEmpaque(UtilsToken.TOKEN_CONTENT,materialesEmpaque)

    @MainThread
    fun getEmpaqueById(materialesEmpaqueId:Int): Flow<MaterialesEmpaque> = servicioProductoApi.getEmpaqueById(UtilsToken.TOKEN_CONTENT,materialesEmpaqueId)

    @MainThread
    fun getEmpaqueById2(materialesEmpaqueId:String) = servicioProductoApi.getEmpaqueById2(UtilsToken.TOKEN_CONTENT,materialesEmpaqueId)

    @MainThread
    fun getAllEmpaquesFiltro(materialesEmpaque: MaterialesEmpaque): Flow<List<MaterialesEmpaque>> = servicioProductoApi.getEmpaquesFiltro(UtilsToken.TOKEN_CONTENT,materialesEmpaque)

    @MainThread
    suspend fun updateEmpaqueById(materialesEmpaque: MaterialesEmpaque)=servicioProductoApi.updateEmpaqueId(UtilsToken.TOKEN_CONTENT,materialesEmpaque.id!!,materialesEmpaque)

    @MainThread
    suspend fun deleteEmpaqueById(materialesEmpaqueId: Int)=servicioProductoApi.deleteEmpaqueId(UtilsToken.TOKEN_CONTENT,materialesEmpaqueId)
}