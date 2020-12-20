package pe.edu.upeu.calidadservupeu.model



data class MateriasPrimas (
    var id:Int?=0,
    var nombre:String?=null,
    var stock:Int?=0,
    var descripcion:String?=null,
    var almacen:String?=null,
    var unidad:String?=null,
    var origen:String?=null,
    var imagen_materias_primas:String?=null,
    var codigo_materia_prima:String?=null
)