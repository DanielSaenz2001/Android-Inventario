package pe.edu.upeu.calidadservupeu.model

import java.util.*

data class ProductDespachos(
    var id:Int?=0,
    var vehiculo:String?=null,
    var nombreConductor:String?=null,
    var fecha:String?=null,
    var ciudadDestino:String?=null,
    var responsable:Int?=0,
    var responsable_nombre:String?=null,
)


