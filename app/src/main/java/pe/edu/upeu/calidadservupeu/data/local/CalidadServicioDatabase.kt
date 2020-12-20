package pe.edu.upeu.calidadservupeu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upeu.calidadservupeu.model.Area
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.model.Subarea
import pe.edu.upeu.calidadservupeu.data.local.dao.ProductoDao

@Database(
    entities = arrayOf(Producto::class,Area::class,Subarea::class), version = DatabaseMigrations.DB_VERSION
)
abstract class CalidadServicioDatabase : RoomDatabase() {
    abstract fun getProductoDao(): ProductoDao
    companion object {
        const val DB_NAME = "calidad_servicio"

        @Volatile
        private var INSTANCE: CalidadServicioDatabase? = null

        fun getInstance(context: Context): CalidadServicioDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalidadServicioDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}