package pe.edu.upeu.calidadservupeu.model

data class IntMateDetalles(
    var id:Int?=0,
    var cantidad_empaque:Int?=0,
    var material_empaque_nombre:String?=null,
    var material_empaque_imagen:String?=null,
    var in_materiales_empaque_id:Int?=0,
    var calidad:String?=null,
    var laminacion:Int?=0,
    var color:String?=null,

    var cantidad_prima:Int?=0,
    var materia_prima_nombre:String?=null,
    var materia_prima_imagen:String?=null,
    var in_materias_prima_id:String?=null,
    var integridad:String?=null,
    var plagas:Int?=0,
    var materias_extranas:Int?=0,
)