package pe.edu.upeu.calidadservupeu.model

import androidx.room.*

@Entity(tableName = "area")
data class Area(
    @PrimaryKey
    @ColumnInfo(name = "id_area") var idArea:Int?=0,
    @ColumnInfo(name = "nombre_a") var nombreA:String?=null,
    @ColumnInfo(name = "estado_a") var estadoA:String?=null,
    @ColumnInfo(name = "descripcion_a") var descripcionA:String?=null
)
