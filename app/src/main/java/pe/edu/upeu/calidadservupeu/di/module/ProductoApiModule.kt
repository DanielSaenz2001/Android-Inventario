package pe.edu.upeu.calidadservupeu.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import pe.edu.upeu.calidadservupeu.data.remote.ServiciosCalidadApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ProductoApiModule {

@Singleton
@Provides
fun provideRetrofitService(): ServiciosCalidadApi=Retrofit.Builder()
    .baseUrl(ServiciosCalidadApi.SERVICIO_CALIDAD_API_URL)
    .addCallAdapterFactory(FlowCallAdapterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    )
    ).build().create(ServiciosCalidadApi::class.java)
}