package pe.edu.upeu.calidadservupeu.model

data class ControlInventarioDetalles(
    var id:Int?=0,
    var producto_nombre:String?=null,
    var producto_imagen:String?=null,
    var unidad:String?=null,
    var producto_cantidad:Int?=0,
    var estado:String?=null,
    var observacion:String?=null,
    var descripcion:String?=null,
)