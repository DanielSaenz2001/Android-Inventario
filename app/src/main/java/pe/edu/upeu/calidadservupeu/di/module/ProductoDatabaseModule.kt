package pe.edu.upeu.calidadservupeu.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import pe.edu.upeu.calidadservupeu.data.local.CalidadServicioDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ProductoDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application:Application)=CalidadServicioDatabase.getInstance(application)
    @Singleton
    @Provides
    fun provideProductoDao(database: CalidadServicioDatabase)=database.getProductoDao()

}