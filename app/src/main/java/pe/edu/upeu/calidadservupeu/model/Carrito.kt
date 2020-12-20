package pe.edu.upeu.calidadservupeu.model

data class Carrito(
    var id:Int?=0,
    var producto_id:Int?=0,
    var materias_primas_id:Int?=0,
    var cantidad_producto:Int?=0,
    var cantidad_empaque:Int?=0,
    var cantidad_materias:Int?=0,
    var tipo:String?=null,
    var user_id:Int?=null,
    var empaque_id:Int?=0,
    /** Vista  **/

    var producto_nombre:String?=null,
    var productos_stock:Int?=0,
    var producto_imagen:String?=null,
    var empaque_nombre:String?=null,
    var empaques_stock:Int?=0,
    var empaque_imagen:String?=null,
    var materia_prima_nombre:String?=null,
    var materia_prima_stock:Int?=0,
    var materia_prima_imagen:String?=null,


)