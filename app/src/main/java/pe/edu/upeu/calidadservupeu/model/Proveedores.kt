package pe.edu.upeu.calidadservupeu.model

data class Proveedores(
    var id:Int?=0,
    var nombre:String?=null,
    var ruc:String?=null,
    var direccion:String?=null,
    var tipo:String?=null,
    var correo_electronico:String?=null,
    var responsable:String?=null,
    var imagen_proveedores:String?=null,
    var telefono:String?=null,
    var codigo_proveedor:String?=null
)