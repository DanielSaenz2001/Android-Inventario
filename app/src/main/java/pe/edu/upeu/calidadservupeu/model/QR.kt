package pe.edu.upeu.calidadservupeu.model

data class QR(
    var id:Int?=0,
    var name:String?=null,
    var rol:String?=null,
    var precio_total:Float?=0.0f,
    var stock:Int?=0,
    var nombre:String?=null,
    var ruc:String?=null,
    var imagen_user:String?=null,
    var imagen_producto:String?=null,
    var imagen_proveedores:String?=null,
    var imagen_materias_primas:String?=null,
    var imagen_material_empaques:String?=null,
)