package pe.edu.upeu.calidadservupeu.model

import java.util.*

data class Produccion(
    var id:Int?=0,
    var fecha:String?=null,
    var cantidad_producto:Int?=0,
    var nombre_producto:String?=null,
    var imagen_producto:String?=null,
    var responsable:Int?=0,
    var producto_id:Int?=0,
    var responsable_nombre:String?=null,
)


