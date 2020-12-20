package pe.edu.upeu.calidadservupeu.model

import androidx.room.*

@Entity(tableName = "subarea")
data class Subarea (
    @PrimaryKey
    @ColumnInfo(name = "id_subarea") var idSubarea:Int?=0,
    @ColumnInfo(name = "nombre_sa") var nombreSa:String?=null,
    @ColumnInfo(name = "estado_sa") var estado_sa:String?=null,
    @ColumnInfo(name = "descripcion_sa") var descripcionSa:String?=null,
    @ColumnInfo(name = "id_area") var idArea:Int?=0,
)

data class AreaWithSubarea(
    @Embedded val user: Area,
    @Relation(parentColumn = "idArea",
        entityColumn = "idArea"
    )
    val servPuestoCollection: List<Area>
)
