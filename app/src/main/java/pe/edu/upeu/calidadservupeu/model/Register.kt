package pe.edu.upeu.calidadservupeu.model

import androidx.room.Entity

@Entity(tableName = "users")
data class Register(
    val email: String,
    val password: String,
    val password_confirmation: String
)