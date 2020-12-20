package pe.edu.upeu.calidadservupeu.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.model.QR
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MateriasPrimasRepository @Inject constructor(
    private val servicioProductoApi: ServiciosCalidadApi
) {
    fun getAllMateriasPrimas(): Flow<List<MateriasPrimas>> = servicioProductoApi.getMateriasPrimas(
        UtilsToken.TOKEN_CONTENT)

    @MainThread
    suspend fun insertMateriaPrima(materiasPrimas: MateriasPrimas) = servicioProductoApi.addMateriaPrima(UtilsToken.TOKEN_CONTENT,materiasPrimas)

    @MainThread
    fun getMateriaPrimaById(materiasPrimasId:Int): Flow<MateriasPrimas> = servicioProductoApi.getMateriaPrimaById(UtilsToken.TOKEN_CONTENT,materiasPrimasId)

    @MainThread
    fun getMateriaPrimaById2(materiasPrimasId:String) = servicioProductoApi.getMateriaPrimaById2(UtilsToken.TOKEN_CONTENT,materiasPrimasId)

    @MainThread
    fun getAllMateriasPrimasFiltro(materiasPrimas: MateriasPrimas): Flow<List<MateriasPrimas>> = servicioProductoApi.getMateriasPrimasFiltro(UtilsToken.TOKEN_CONTENT,materiasPrimas)

    @MainThread
    suspend fun updateMateriaPrimaById(materiasPrimas: MateriasPrimas)=servicioProductoApi.updateMateriaPrimaId(UtilsToken.TOKEN_CONTENT,materiasPrimas.id!!,materiasPrimas)

    @MainThread
    suspend fun deleteMateriaPrimaById(materiasPrimasId: Int)=servicioProductoApi.deleteMateriaPrimaId(UtilsToken.TOKEN_CONTENT,materiasPrimasId)
}