package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_regulaciones_create.*
import kotlinx.android.synthetic.main.activity_regulaciones_create.cantidadRegulacion
import kotlinx.android.synthetic.main.activity_regulaciones_create.productoRegulacion
import kotlinx.android.synthetic.main.activity_regulaciones_create.view.*
import kotlinx.android.synthetic.main.activity_regulaciones_create.view.btnGuardarRegulacion
import kotlinx.android.synthetic.main.form_productos_despachos.*
import kotlinx.android.synthetic.main.form_productos_despachos.view.*
import kotlinx.android.synthetic.main.form_regulacion.*
import kotlinx.android.synthetic.main.form_regulacion.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityRegulacionesCreateBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.activity.MaterialEmpaques
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.activity.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.producto.activity.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import java.util.*

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RegulacionesCreate :  BaseActivity<RegulacionesListViewModel, ActivityRegulacionesCreateBinding>()   {
    companion object{
        const val tipo="tipo"
    }
    override val mViewModel: RegulacionesListViewModel by viewModels()
    private  lateinit var carrito: Carrito
    override fun getViewBinding(): ActivityRegulacionesCreateBinding = ActivityRegulacionesCreateBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        val tipo=intent.extras?.getString(tipo)?: throw IllegalArgumentException("DetallesId es nulo")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        UtilsToken.regulacion=tipo
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        val values : Array<String> = arrayOf("Aumentar", "Disminuir")
        val convert_from_spinner= mViewBinding.spinnerActividad
        convert_from_spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, values)
        if(tipo=="prima"){
            getCarrito(1)
        }
        if(tipo=="empaque"){
            getCarrito(2)
        }
        if(tipo=="producto"){
            getCarrito(3)
        }
        mViewBinding.btnDeleteRegulacion.setOnClickListener {
            if(carrito.producto_nombre !==null){
                mViewModel.deleteCarrito(carrito.id!!)
                if(tipo=="prima"){
                    getCarrito(1)
                    supportActionBar?.title = "Materia prima"
                }
                if(tipo=="empaque"){
                    getCarrito(2)
                    supportActionBar?.title = "Materiales de empaque"
                }
                if(tipo=="producto"){
                    getCarrito(3)
                    supportActionBar?.title = "Productos elaborados"
                }
            }
        }
        mViewBinding.btnCantidadRegulacion.setOnClickListener {
            if(carrito.cantidad_producto!! > 0){
                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_regulacion,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)
                val mAlertDialog = mBuild.show()
                mAlertDialog.txtCantidadRegulacion.setText(carrito.cantidad_producto.toString())
                mDialogView.btnGuardarRegulacion.setOnClickListener {
                    carrito.cantidad_producto = mDialogView.txtCantidadRegulacion.text.toString().toInt()


                    mViewModel.updateCarrito(carrito,this)
                    mAlertDialog.dismiss()
                    if(tipo=="prima"){
                        getCarrito(1)
                    }
                    if(tipo=="empaque"){
                        getCarrito(2)
                    }
                    if(tipo=="producto"){
                        getCarrito(3)
                    }
                }
                mDialogView.btnCancelarRegulacion.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }
        }
        mViewBinding.btnGuardarRegulacion.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c[Calendar.DAY_OF_MONTH]
            val month = c[Calendar.MONTH]
            val year = c[Calendar.YEAR]
            var fecha= year.toString() +"-"+ (month+1).toString() + "-"+day.toString()


            if(tipo=="prima"){
                val regulacionX = Regulaciones()
                regulacionX.cantidad=carrito.cantidad_producto
                regulacionX.motivo = mViewBinding.editTextTextMultiLine.text.toString()
                regulacionX.actividad = mViewBinding.spinnerActividad.selectedItem.toString()
                regulacionX.fecha=fecha
                regulacionX.prima_id = carrito.materias_primas_id
                mViewModel.createRegulacion(regulacionX)
            }
            if(tipo=="empaque"){
                val regulacionX = Regulaciones()
                regulacionX.cantidad=carrito.cantidad_producto
                regulacionX.motivo = mViewBinding.editTextTextMultiLine.text.toString()
                regulacionX.actividad = mViewBinding.spinnerActividad.selectedItem.toString()
                regulacionX.fecha=fecha
                regulacionX.empaque_id = carrito.empaque_id
                mViewModel.createRegulacion(regulacionX)
            }
            if(tipo=="producto"){
                val regulacionX = Regulaciones()
                regulacionX.cantidad=carrito.cantidad_producto
                regulacionX.motivo = mViewBinding.editTextTextMultiLine.text.toString()
                regulacionX.actividad = mViewBinding.spinnerActividad.selectedItem.toString()
                regulacionX.fecha=fecha
                regulacionX.producto_id= carrito.producto_id
                mViewModel.createRegulacion(regulacionX)
            }
            mViewBinding.editTextTextMultiLine.setText("")
            mViewModel.deleteCarrito(carrito.id!!)
            if(tipo=="prima"){
                getCarrito(1)
            }
            if(tipo=="empaque"){
                getCarrito(2)
            }
            if(tipo=="producto"){
                getCarrito(3)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_main_operation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val tipo=intent.extras?.getString(RegulacionesList.tipo)?: throw IllegalArgumentException("DetallesId es nulo")
                val intent = Intent(this, RegulacionesList::class.java)
                intent.putExtra(RegulacionesList.tipo,tipo)
                startActivity(intent)
                return true
            }
            R.id.action_theme -> {
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_NO
                    ) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                AppCompatDelegate.setDefaultNightMode(mode)
                return true
            }

            R.id.action_producto_add -> {
                val tipo=intent.extras?.getString(RegulacionesList.tipo)?: throw IllegalArgumentException("DetallesId es nulo")
                Log.i("TIPO",tipo)
                if(tipo=="prima"){
                    val intent = Intent(this, MateriasPrimas::class.java)
                    intent.putExtra(MateriasPrimas.TIPO_ACTIVIDAD, "Regulaciones")
                    UtilsToken.tipo_actividad_regulaciones="Prima"
                    startActivity(intent)
                }
                if(tipo=="empaque"){
                    val intent = Intent(this, MaterialEmpaques::class.java)
                    intent.putExtra(MaterialEmpaques.TIPO_ACTIVIDAD, "Regulaciones")
                    UtilsToken.tipo_actividad_regulaciones="Empaque"
                    startActivity(intent)
                }
                if(tipo=="producto"){
                    val intent = Intent(this, Producto::class.java)
                    intent.putExtra(Producto.TIPO_ACTIVIDAD, "Regulaciones")
                    UtilsToken.tipo_actividad_regulaciones="Producto"
                    startActivity(intent)
                }

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    fun getCarrito(id:Int) {
        mViewModel.getCarrito(id).observe(this) { carritoX ->
            mViewBinding.Regulacion.apply {
                carrito = carritoX
                productoRegulacion.text = carrito.producto_nombre
                cantidadRegulacion.text = carrito.cantidad_producto.toString()
                mViewBinding.imagenProducto.load(carrito.producto_imagen.toString()) {
                    placeholder(R.drawable.ic_photo)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }
}