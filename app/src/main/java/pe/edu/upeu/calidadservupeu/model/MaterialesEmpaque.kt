package pe.edu.upeu.calidadservupeu.model



data class MaterialesEmpaque (
    var id:Int?=0,
    var nombre:String?=null,
    var stock:Int?=0,
    var descripcion:String?=null,
    var peso_neto:Float?=0.0f,
    var peso_bruto:Float?=0.0f,
    var medidas:String?=null,
    var imagen_material_empaques:String?=null,
    var codigo_materiales_empaques:String?=null
)