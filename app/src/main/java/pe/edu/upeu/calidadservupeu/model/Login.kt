package pe.edu.upeu.calidadservupeu.model


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class Login(
    var email: String = "",
    var password: String = ""
)