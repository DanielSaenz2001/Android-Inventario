package pe.edu.upeu.calidadservupeu.model

data class User (
    var id:Int?=0,
    var name:String?=null,
    var email:String?=null,
    var rol:String?=null,
    var area:String?=null,
    var autorizado:Int?=null,
    var imagen_user:String?=null,

    var password:String?=null,
    var password_new:String?=null,
    var password_confirmation:String?=null,

    var email_verified_at:String?=null,
    var created_at:String?=null,
    var updated_at:String?=null
)
