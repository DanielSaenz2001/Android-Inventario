package pe.edu.upeu.calidadservupeu.model

data class credenciales (
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val user: String,

)