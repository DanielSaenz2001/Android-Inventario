package pe.edu.upeu.calidadservupeu.ui.qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_qr.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityQrBinding
import pe.edu.upeu.calidadservupeu.model.*
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create.ProductosDespachosCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.details.MaterialesEmpaqueDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.details.MateriasPrimasDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity
import pe.edu.upeu.calidadservupeu.ui.proveedor.details.ProveedorDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.RegulacionesCreate
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class QrActivity : BaseActivity<QrViewModel, ActivityQrBinding>() ,ZBarScannerView.ResultHandler{


    private lateinit var mView: View
    private  lateinit var materialesEmpaque: MaterialesEmpaque
    private  lateinit var materiasPrimas: MateriasPrimas
    private  lateinit var producto: Producto
    private val CAMERA_PERMISSION_REQUEST_CODE = 1000


    override val mViewModel: QrViewModel by viewModels()
    var scannerView: ZBarScannerView? = null
    private lateinit var viewModel: QrViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {

        } else {
            requestPermission()
        }


        viewModel = ViewModelProviders.of(this).get(QrViewModel::class.java)
        mView=mViewBinding.root
        if(UtilsToken.tipo_actividad == ""){
            mViewBinding.btnSelectUserQr.visibility = View.INVISIBLE
        }
        initializeQRCamera()
        val QrBusqueda=intent.extras?.getString(TIPO_BUSQUEDA)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Busqueda por URL"


        mViewBinding.imageViewQr.setOnClickListener {
            val tipo=intent.extras?.getString(TIPO_BUSQUEDA)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
            if(UtilsToken.tipo_actividad ==""){
                if(tipo == "PRODUCT"){
                    val intent= Intent(this, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.PRODUCT_ID, mViewBinding.idQr.text.toString().toInt())
                    startActivity(intent)
                }
                if(tipo == "PROVENDER"){
                    val intent= Intent(this, ProveedorDetailsActivity::class.java)
                    intent.putExtra(ProveedorDetailsActivity.PROVEEDOR_ID, mViewBinding.idQr.text.toString().toInt())
                    startActivity(intent)
                }
                if(tipo == "PRIMAL"){
                    val intent= Intent(this, MateriasPrimasDetailsActivity::class.java)
                    intent.putExtra(MateriasPrimasDetailsActivity.MATERIASPRIMAS_ID, mViewBinding.idQr.text.toString().toInt())
                    startActivity(intent)
                }
                if(tipo == "EMPAQUE"){
                    val intent= Intent(this, MaterialesEmpaqueDetailsActivity::class.java)
                    intent.putExtra(MaterialesEmpaqueDetailsActivity.MATERIALESEMPAQUE_ID, mViewBinding.idQr.text.toString().toInt())
                    startActivity(intent)
                }
            }
        }
        mViewBinding.btnSelectUserQr.setOnClickListener {
            Log.i("TIPO DE ACTIVIDAD:::",UtilsToken.tipo_actividad)
            if(UtilsToken.tipo_actividad =="Regulaciones"){
                if(UtilsToken.tipo_actividad_regulaciones =="Prima"){
                    selectProductoPrima(mViewBinding.idQr.text.toString().toInt())
                }
                if(UtilsToken.tipo_actividad_regulaciones =="Empaque"){
                    selectEmpaque(mViewBinding.idQr.text.toString().toInt())
                }
                if(UtilsToken.tipo_actividad_regulaciones =="Producto"){
                    selectProducto(mViewBinding.idQr.text.toString().toInt(),mViewBinding.userNameQr.text.toString())
                }
            }
            if(UtilsToken.tipo_actividad =="Crear_Despacho_Producto"){
                if (UtilsToken.subeventos =="Cambiar Empaque"){
                    selectEmpaque(mViewBinding.idQr.text.toString().toInt())
                }
                if (UtilsToken.subeventos =="Escoger Producto"){
                    selectProducto(mViewBinding.idQr.text.toString().toInt(),mViewBinding.userNameQr.text.toString())
                }
            }
            if(UtilsToken.tipo_actividad =="Crear_Produccion"){
                if (UtilsToken.subeventos =="Cambiar Producto"){
                    selectProducto(mViewBinding.idQr.text.toString().toInt(),mViewBinding.userNameQr.text.toString())
                }
                if(UtilsToken.subeventos=="Escoger Prima"){
                    selectProductoPrima(mViewBinding.idQr.text.toString().toInt())
                }
            }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(this)
        scannerView!!.setResultHandler(this)
        scannerView!!.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.design_default_color_on_primary
            )
        )
        scannerView!!.setBorderColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        scannerView!!.setLaserColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        scannerView!!.setBorderStrokeWidth(10)
        scannerView!!.setSquareViewFinder(true)
        scannerView!!.setupScanner()
        scannerView!!.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }
    private fun startQRCamera() {
        scannerView!!.startCamera()
    }
    override fun onResume() {
        super.onResume()
        scannerView!!.setResultHandler(this)
        scannerView!!.startCamera()
    }
    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView!!.flash = true
    }
    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView!!.flash = false
    }
    override fun onPause() {
        super.onPause()
        scannerView!!.stopCamera()
    }
    override fun onDestroy() {
        super.onDestroy()
        scannerView!!.stopCamera()
    }
    fun datoprueba(id:String,tipo: String){
        if(tipo == "PRODUCT"){
            mViewModel.productoById(id).observe(this){
                variable->mViewBinding.DatosScanner.apply {
                    producto=variable
                    user_nameQr.text=producto.nombre
                    user_tipoQr.text=producto.stock.toString()
                    imageViewQr.load(producto.imagen_producto){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    idQr.text= producto.id.toString()
                    if(producto.id == 0){
                        mViewBinding.DatosScanner.visibility=View.INVISIBLE
                    }
                    if(producto.id !== 0){
                        mViewBinding.DatosScanner.visibility=View.VISIBLE
                    }
                }
            }
        }
        if(tipo == "PRIMAL"){
            mViewModel.materiaById(id).observe(this){
                variable->mViewBinding.DatosScanner.apply {
                    materiasPrimas=variable
                    user_nameQr.text=materiasPrimas.nombre
                    user_tipoQr.text=materiasPrimas.stock.toString()
                    imageViewQr.load(materiasPrimas.imagen_materias_primas){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    idQr.text= materiasPrimas.id.toString()
                    if(materiasPrimas.id == 0){
                        mViewBinding.DatosScanner.visibility=View.INVISIBLE
                    }
                    if(materiasPrimas.id !== 0){
                        mViewBinding.DatosScanner.visibility=View.VISIBLE
                    }
                }
            }
        }
        if(tipo == "EMPAQUE"){
            mViewModel.empaqueById(id).observe(this){

                variable->mViewBinding.DatosScanner.apply {
                    materialesEmpaque = variable
                    user_nameQr.text=materialesEmpaque.nombre
                    user_tipoQr.text=materialesEmpaque.stock.toString()
                    imageViewQr.load(materialesEmpaque.imagen_material_empaques){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    idQr.text= materialesEmpaque.id.toString()
                    Log.i("DATOES EMPAUQE", materialesEmpaque.toString())
                    if(materialesEmpaque.id == 0){
                        mViewBinding.DatosScanner.visibility=View.INVISIBLE
                    }
                    if(materialesEmpaque.id !== 0){
                        mViewBinding.DatosScanner.visibility=View.VISIBLE
                    }
                }
            }

        }
    }
    override fun handleResult(rawResult: Result?) {
        val QrBusqueda=intent.extras?.getString(TIPO_BUSQUEDA)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
        datoprueba(rawResult?.contents!!.toString(),QrBusqueda)
        scannerView!!.resumeCameraPreview(this)
    }
    override fun getViewBinding(): ActivityQrBinding = ActivityQrBinding.inflate(layoutInflater)
    companion object{
        const val TIPO_BUSQUEDA="tipo"
        const val TIPO_ACTIVIDAD="tipo"
        fun newInstance(): QrActivity{return QrActivity()}
    }

    private fun selectProductoPrima(materiasPrimas: Int){
        Log.i("dato",materiasPrimas.toString())

        if(UtilsToken.tipo_actividad == "Crear_Produccion"){
            val carritoX= Carrito()
            carritoX.materias_primas_id=materiasPrimas
            mViewModel.createCarritoProduccion(carritoX,this)
            val intent = Intent(this, ProduccionCreate::class.java)
            startActivity(intent)
        }

        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX= Carrito()
            carritoX.materias_primas_id=materiasPrimas
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"prima")
            startActivity(intent)
        }
    }
    private fun selectProducto(productoid: Int,productonombre:String){
        if(UtilsToken.tipo_actividad == "Crear_Produccion"){
            UtilsToken.id_producto=productoid
            UtilsToken.nombre_producto= productonombre
            val intent = Intent(this, ProduccionCreate::class.java)
            startActivity(intent)
        }
        if(UtilsToken.tipo_actividad =="Crear_Despacho_Producto"){
            val carritoX=Carrito()
            carritoX.producto_id=productoid
            mViewModel.createCarritoDespacho(carritoX,this)
            val intent = Intent(this, ProductosDespachosCreate::class.java)
            startActivity(intent)
        }

        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX=Carrito()
            carritoX.producto_id=productoid
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"producto")
            startActivity(intent)
        }
    }
    private fun selectEmpaque(materialEmpaquesid: Int){

        if(UtilsToken.tipo_actividad == "Crear_Despacho_Producto"){
            val carritoX= Carrito()
            carritoX.id = UtilsToken.id_carrito.toInt()
            carritoX.empaque_id=materialEmpaquesid
            mViewModel.updateCarritoDespacho(carritoX)
            val intent = Intent(this, ProductosDespachosCreate::class.java)
            startActivity(intent)
        }
        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX=Carrito()
            carritoX.empaque_id=materialEmpaquesid
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"empaque")
            startActivity(intent)
        }
    }
    private fun requestPermission() {
        val perm =ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }
}