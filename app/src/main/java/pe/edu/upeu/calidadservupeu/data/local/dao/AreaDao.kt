package pe.edu.upeu.calidadservupeu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.model.Area

@Dao
interface AreaDao {
    @Query("SELECT * FROM area")
    fun getAllArea(): Flow<List<Area>>

    @Query("SELECT * FROM area where id_area=:idarea")
    fun getAreaById(idarea:Int): Flow<Area>

    @Query("delete from area")
    suspend fun deleteAllArea()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(producto:List<Area>)
}