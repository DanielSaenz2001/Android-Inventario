package pe.edu.upeu.calidadservupeu.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
   @PrimaryKey
   var id:Int?=0,
   var nombre:String?=null,
   var stock:Int?=0,
   var unidad:String?=null,
   var descripcion:String?=null,
   var modelo:String?=null,
   var precio_unitario:Float?=0.0f,
   var IVA:Int?=0,
   var precio_total:Float?=0.0f,
   var mensaje:String?=null,
   var codigo_producto:String?=null,
   var imagen_producto:String?=null
)