package pe.edu.upeu.calidadservupeu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.model.Area
import pe.edu.upeu.calidadservupeu.model.Subarea

@Dao
interface SubareaDao {
    @Query("SELECT * FROM subarea")
    fun getAllSubrea(): Flow<List<Subarea>>

    @Query("SELECT * FROM subarea where id_subarea=:idsubarea")
    fun getSubreaById(idsubarea:Int): Flow<Subarea>

    @Query("delete from subarea")
    suspend fun deleteAllSubrea()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubrea(subarea:List<Subarea>)
}