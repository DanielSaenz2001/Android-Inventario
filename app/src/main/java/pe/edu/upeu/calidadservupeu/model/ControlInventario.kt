package pe.edu.upeu.calidadservupeu.model

data class ControlInventario(
    var id:Int?=0,
    var fecha_inicio:String?=null,
    var fecha_fin:String?=null,
    var estado:String?=null,
    var responsable:String?=null,
    var imagen_user:String?=null,
    var tipo:String?=null,
)