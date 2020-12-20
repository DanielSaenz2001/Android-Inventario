package pe.edu.upeu.calidadservupeu.data.remote

import kotlinx.coroutines.flow.Flow
import pe.edu.upeu.calidadservupeu.data.repository.DespachosRepository
import pe.edu.upeu.calidadservupeu.model.*
import retrofit2.Response
import retrofit2.http.*

interface ServiciosCalidadApi {



    @GET("/api/mateempaque") //
    fun getEmpaques(@Header("Authorization") token:String): Flow<List<MaterialesEmpaque>>

    @POST("/api/mateempaquefiltro") //
    fun getEmpaquesFiltro(@Header("Authorization") token:String,@Body materialesEmpaque: MaterialesEmpaque): Flow<List<MaterialesEmpaque>>

    @GET("/api/mateempaque/{id}")
    fun getEmpaqueById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<MaterialesEmpaque>

    @GET("/api/mateempaque2/{id}")
    fun getEmpaqueById2(@Header("Authorization") token:String,@Path("id") id: String): Flow<MaterialesEmpaque>

    @POST("/api/mateempaque") //
    suspend fun addEmpaque(@Header("Authorization") token:String,@Body materialesEmpaque: MaterialesEmpaque):Response<Void>

    @DELETE("/api/mateempaque/{id}")
    suspend fun deleteEmpaqueId(@Header("Authorization") token:String,@Path("id") id:Int):Response<Void>

    @PUT("/api/mateempaque/{id}")
    suspend fun updateEmpaqueId(@Header("Authorization") token:String,@Path("id") id:Int,  @Body materialesEmpaque:MaterialesEmpaque):Response<Void>








    /********************************/







    @GET("/api/mateprimas") //
    fun getMateriasPrimas(@Header("Authorization") token:String): Flow<List<MateriasPrimas>>

    @POST("/api/mateprimasfiltro") //
    fun getMateriasPrimasFiltro(@Header("Authorization") token:String,@Body materiasPrimas: MateriasPrimas): Flow<List<MateriasPrimas>>

    @GET("/api/mateprimas/{id}")
    fun getMateriaPrimaById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<MateriasPrimas>

    @GET("/api/mateprimas2/{id}")
    fun getMateriaPrimaById2(@Header("Authorization") token:String,@Path("id") id: String): Flow<MateriasPrimas>

    @POST("/api/mateprimas") //
    suspend fun addMateriaPrima(@Header("Authorization") token:String,@Body materiasPrimas: MateriasPrimas):Response<Void>

    @DELETE("/api/mateprimas/{id}")
    suspend fun deleteMateriaPrimaId(@Header("Authorization") token:String,@Path("id") id:Int):Response<Void>

    @PUT("/api/mateprimas/{id}")
    suspend fun updateMateriaPrimaId(@Header("Authorization") token:String,@Path("id") id:Int,  @Body materiasPrimas:MateriasPrimas):Response<Void>




    /*****************/
    /********************************/








    @GET("/api/productos") //
    fun getProductos(@Header("Authorization") token:String): Flow<List<Producto>>

    @POST("/api/productosfiltro") //
    fun getProductosFiltro(@Header("Authorization") token:String,@Body producto: Producto): Flow<List<Producto>>

    @GET("/api/productos/{id}")
    fun getProductoById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<Producto>
    @GET("/api/productos2/{id}")
    fun getProductoById2(@Header("Authorization") token:String,@Path("id") id: String): Flow<Producto>

    @POST("/api/productos") //
    suspend fun addProducto(@Header("Authorization") token:String,@Body producto: Producto):Response<Void>

    @DELETE("/api/productos/{id}")
    suspend fun deleteProductoId(@Header("Authorization") token:String,@Path("id") id:Int):Response<Void>

    @PUT("/api/productos/{id}")
    suspend fun updateProductoId(@Header("Authorization") token:String,@Path("id") id:Int,  @Body producto:Producto):Response<Void>




    /********************************/










    @GET("/api/carritoRegulacion/{id}") //
    fun getCarritoRegulacion(@Header("Authorization") token:String,@Path("id") id:Int): Flow<Carrito>

    @POST("/api/carritoRegulacion") //
    suspend fun createCarritoRegulacion(@Header("Authorization") token:String,@Body carrito:Carrito): Response<Void>

    @PUT("/api/carritoRegulacion/{id}") //
    suspend fun updateCarritoRegulacionById(@Header("Authorization") token:String,@Path("id") id:Int,  @Body carrito:Carrito): Response<Void>

    @GET("/api/carritoDespachos") //
    fun getCarritoDespacho(@Header("Authorization") token:String): Flow<List<Carrito>>

    @PUT("/api/carritoDespachoProducto/{id}") //
    suspend fun updateCarritoDespachoProductoById(@Header("Authorization") token:String,@Path("id") id:Int,  @Body carrito:Carrito): Response<Void>

    @PUT("/api/carritoDespachoEmpaque/{id}") //
    suspend fun updateCarritoDespachoEmpaqueById(@Header("Authorization") token:String,@Path("id") id:Int,  @Body carrito:Carrito): Response<Void>

    @POST("/api/carritoDespacho") //
    suspend fun createCarritoDespacho(@Header("Authorization") token:String,@Body carrito:Carrito): Response<Void>

    @DELETE("/api/carrito/{id}")
    suspend fun deleteCarritoById(@Header("Authorization") token:String,@Path("id") id:Int):Response<Void>

    @GET("/api/carritoProduccion") //
    fun getCarritoProduccion(@Header("Authorization") token:String): Flow<List<Carrito>>

    @POST("/api/carritoProduccion") //
    suspend fun createCarritoProduccion(@Header("Authorization") token:String,@Body carrito:Carrito): Response<Void>

    @PUT("/api/carritoProduccion/{id}") //
    suspend fun updateCarritoProduccionById(@Header("Authorization") token:String,@Path("id") id:Int,  @Body carrito:Carrito): Response<Void>








    /********************************/


    @GET("/api/productosDespachos")
    fun getAllDespacho(@Header("Authorization") token:String): Flow<List<ProductDespachos>>

    @GET("/api/productosDespachos/{id}")
    fun getDespachoById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<ProductDespachos>

    @GET("/api/productosDespachosDetails/{id}")
    fun getDespachoDetailsById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<List<ProductosDespachosDetalles>>

    @POST("/api/productosDespachos") //
    suspend fun addProductosDespachos(@Header("Authorization") token:String,@Body productDespachos: ProductDespachos):Response<Void>


    @GET("/api/productosProduccion")
    fun getAllProduccion(@Header("Authorization") token:String): Flow<List<Produccion>>

    @GET("/api/productosProduccion/{id}")
    fun getProduccionById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<Produccion>

    @GET("/api/productosProduccionDetails/{id}")
    fun getProduccionDetailsById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<List<ProduccionDetalles>>

    @POST("/api/productosProduccion") //
    suspend fun addProductosProduccion(@Header("Authorization") token:String,@Body produccion: Produccion):Response<Void>









    /********************************/


    @GET("/api/user2/{id}")
    fun getUserById2(@Header("Authorization") token:String,@Path("id") id: String): Flow<List<QR>>

    @PUT("/api/userImagen")
    suspend fun cambiarImagenUser(@Header("Authorization") token:String,@Body user: User):Response<Void>


    /********************************/




    @GET("/api/proveedores") //
    fun getProveedores(@Header("Authorization") token:String): Flow<List<Proveedores>>

    @POST("/api/proveedoresfiltro") //
    fun getProveedoresFiltro(@Header("Authorization") token:String,@Body proveedores: Proveedores): Flow<List<Proveedores>>

    @GET("/api/proveedores/{id}")
    fun getProveedoresById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<Proveedores>
    @GET("/api/proveedores2/{id}")
    fun getProveedoresById2(@Header("Authorization") token:String,@Path("id") id: String): Flow<List<QR>>

    @POST("/api/proveedores") //
    suspend fun addProveedores(@Header("Authorization") token:String,@Body proveedores: Proveedores):Response<Void>

    @DELETE("/api/proveedores/{id}")
    suspend fun deleteProveedoresId(@Header("Authorization") token:String,@Path("id") id:Int):Response<Void>

    @PUT("/api/proveedores/{id}")
    suspend fun updateProveedoresId(@Header("Authorization") token:String,@Path("id") id:Int,  @Body proveedores: Proveedores):Response<Void>






    /********************************/





    @POST("/api/auth/login")
    suspend fun loginUser(@Body login: Login):Response<credenciales>

    @GET("/api/auth/me")
    suspend fun getProfile(@Header("Authorization") token:String): Response<User>

    @POST("/api/auth/logout")
    suspend fun logout(@Header("Authorization") token:String):Response<Void>

    @POST("/api/auth/recuperar")
    suspend fun cambiarContra(@Header("Authorization") token:String,@Body user: User):Response<User>

    @POST("/api/auth/cambiarNombre")
    suspend fun cambiarNombre(@Header("Authorization") token:String,@Body user: User):Response<Void>






    /*************************/








    @GET("/api/ingresoMateriasPrimas") //
    fun getIngresoMateriasPrimas(@Header("Authorization") token:String): Flow<List<IntMate>>

    @GET("/api/ingresoMateriasPrimas/{id}")
    fun getIngresoMateriasPrimasById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<IntMate>

    @GET("/api/ingresosMaterialesEmpaques") //
    fun getIngresoMaterialesEmpaque(@Header("Authorization") token:String): Flow<List<IntMate>>

    @GET("/api/ingresosMaterialesEmpaques/{id}")
    fun getIngresoMaterialesEmpaqueById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<IntMate>

    @GET("/api/ingresoMateriasPrimasDetalles/{id}")
    fun getIngresoPrimaDetallesById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<List<IntMateDetalles>>

    @GET("/api/ingresoMaterialesEmpaquesDetalles/{id}")
    fun getIngresoEmpaqueDetallesById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<List<IntMateDetalles>>

    /*************************/






    @GET("/api/regulacionesPrima")
    fun getRegulacionesPrimas(@Header("Authorization") token:String): Flow<List<Regulaciones>>

    @GET("/api/regulacionesEmpaque")
    fun getRegulacionesEmpaque(@Header("Authorization") token:String): Flow<List<Regulaciones>>

    @GET("/api/regulacionesProducto")
    fun getRegulacionesProducto(@Header("Authorization") token:String): Flow<List<Regulaciones>>

    @GET("/api/regulaciones/{id}")
    fun getRegulacionesById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<Regulaciones>

    @POST("/api/regulaciones") //
    suspend fun addRegulacion(@Header("Authorization") token:String,@Body regulaciones: Regulaciones):Response<Void>





    /*******************************/






    @GET("/api/controlPrima")
    fun getControlPrimas(@Header("Authorization") token:String): Flow<List<ControlInventario>>

    @GET("/api/controlEmpaque")
    fun getControlEmpaque(@Header("Authorization") token:String): Flow<List<ControlInventario>>

    @GET("/api/controlProducto")
    fun getControlProducto(@Header("Authorization") token:String): Flow<List<ControlInventario>>

    @GET("/api/controlInvetario/{id}")
    fun getControlInventarioDetalles(@Header("Authorization") token:String,@Path("id") id: Int): Flow<List<ControlInventarioDetalles>>

    @PUT("/api/controlInvetario/{id}")
    suspend fun getControlInventarioDetallesUpdateById(@Header("Authorization") token:String,@Path("id") id: Int,@Body control: ControlInventarioDetalles):Response<Void>


    @GET("/api/control/{id}")
    fun getControlInventarioById(@Header("Authorization") token:String,@Path("id") id: Int): Flow<ControlInventario>

    @POST("/api/controlPrima") //
    suspend fun addControlPrima(@Header("Authorization") token:String,@Body controlInventario: ControlInventario):Response<ControlInventario>

    @POST("/api/controlEmpaque") //
    suspend fun addControlEmpaque(@Header("Authorization") token:String,@Body controlInventario: ControlInventario):Response<ControlInventario>

    @POST("/api/controlProducto") //
    suspend fun addControlProducto(@Header("Authorization") token:String,@Body controlInventario: ControlInventario):Response<ControlInventario>

    @GET("/api/controlFinish/{id}")
    suspend fun controlFinish(@Header("Authorization") token:String,@Path("id") id: Int): Response<Void>




    companion object{
        const val SERVICIO_CALIDAD_API_URL="http://ec2-3-239-129-10.compute-1.amazonaws.com:8000"
        //const val SERVICIO_CALIDAD_API_URL="http://192.168.1.35:8000"
    }
}