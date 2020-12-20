package pe.edu.upeu.calidadservupeu.model

data class Regulaciones(
    var id:Int?=0,
    var cantidad:Int?=0,
    var fecha:String?=null,
    var motivo:String?=null,
    var tipo:String?=null,
    var actividad:String?=null,
    var producto_nombre:String?=null,
    var imagen_producto:String?=null,
    var responsable:String?=null,
    var producto_id:Int?=0,
    var prima_id:Int?=0,
    var empaque_id:Int?=0,
)