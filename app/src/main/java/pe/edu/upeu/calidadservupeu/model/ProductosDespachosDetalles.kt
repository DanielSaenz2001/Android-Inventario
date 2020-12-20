package pe.edu.upeu.calidadservupeu.model

data class ProductosDespachosDetalles(
    var id:Int?=0,
    var cantidad_producto:Int?=0,
    var cantidad_empaque:Int?=0,
    var producto_nombre:String?=null,
    var empaque_nombre:String?=null,
    var producto_imagen:String?=null,
    var empaque_imagen:String?=null,
)