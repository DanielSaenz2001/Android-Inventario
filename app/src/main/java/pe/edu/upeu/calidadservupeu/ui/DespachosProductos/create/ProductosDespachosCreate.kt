package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.form_productos_despachos.*
import kotlinx.android.synthetic.main.form_productos_despachos.view.*
import kotlinx.android.synthetic.main.item_producto_despacho.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProductosDespachosCreateBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.ProductosDespachados
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.activity.MaterialEmpaques
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.CarritoListAdapter
import pe.edu.upeu.calidadservupeu.ui.producto.activity.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import java.util.*


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductosDespachosCreate : BaseActivity<ProductosDespachosCreateViewModel, ActivityProductosDespachosCreateBinding>() {
    override val mViewModel: ProductosDespachosCreateViewModel by viewModels()
    override fun getViewBinding(): ActivityProductosDespachosCreateBinding = ActivityProductosDespachosCreateBinding.inflate(
        layoutInflater
    )
    private val mAdapter = CarritoListAdapter(this::onItemClicked)
    var fecha: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.DespachosRecyclerView.adapter = mAdapter
        UtilsToken.tipo_carrito="Despachos"
        supportActionBar?.title = "Vender Productos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        initDespachos()
        mViewBinding.btnGuardar.setOnClickListener {
            val productDespachosX = ProductDespachos()
            productDespachosX.vehiculo = mViewBinding.editTextTextPersonName.text.toString()
            productDespachosX.nombreConductor = mViewBinding.editTextTextPersonName2.text.toString()
            productDespachosX.fecha =fecha.toString()
            productDespachosX.ciudadDestino = mViewBinding.editTextTextPersonName3.text.toString()
            if(productDespachosX.vehiculo == ""){}
            if(productDespachosX.nombreConductor == ""){}
            if(productDespachosX.ciudadDestino == ""){}
            mViewModel.createDespacho(productDespachosX,this)
        }
        mViewBinding.editTextDate.setOnClickListener {
            showDatePickerDialog()
        }
        val c = Calendar.getInstance()
        val day = c[Calendar.DAY_OF_MONTH]
        val month = c[Calendar.MONTH]
        val year = c[Calendar.YEAR]
        fecha= year.toString() +"-"+ (month+1).toString() + "-"+day.toString()
        mViewBinding.editTextDate.setText(fecha.toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_main_operation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, ProductosDespachados::class.java)
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
                val intent = Intent(this, Producto::class.java)
                UtilsToken.subeventos ="Escoger Producto"
                intent.putExtra(Producto.TIPO_ACTIVIDAD, "Crear_Despacho_Producto")
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun onItemClicked(carrito: Carrito, imageView: ImageView, v: View){
        when(v) {
            v.btnDeleteDespacho -> {
                Log.i("Llegara a eliminar", carrito.id.toString())
                deleteDespacho(carrito)
            }

            v.btnEditDespacho -> {
                Log.i("Llegara a abrir un modal", carrito.toString())

                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_productos_despachos,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)

                val mAlertDialog = mBuild.show()
                mAlertDialog.txtStockProductoDespacho.setText(carrito.cantidad_producto.toString())
                mAlertDialog.txtNombreEmpaqueDespacho.setText(carrito.empaque_nombre.toString())
                mAlertDialog.txtStockEmpaqueDespacho.setText(carrito.cantidad_empaque.toString())
                mDialogView.btnGuardarDespacho.setOnClickListener {
                    carrito.cantidad_producto =
                        mDialogView.txtStockProductoDespacho.text.toString().toInt()
                    carrito.cantidad_empaque =
                        mDialogView.txtStockEmpaqueDespacho.text.toString().toInt()
                    editDespacho(carrito)
                    mAlertDialog.dismiss()
                }

                mDialogView.btnCancelarDespacho.setOnClickListener {
                    mAlertDialog.dismiss()
                }
                mDialogView.btnEditEmpaqueDespacho.setOnClickListener {
                    UtilsToken.id_carrito = carrito.id.toString()
                    UtilsToken.subeventos ="Cambiar Empaque"
                    val intent = Intent(this, MaterialEmpaques::class.java)
                    intent.putExtra(MaterialEmpaques.TIPO_ACTIVIDAD, "Crear_Despacho_Producto")
                    startActivity(intent)
                }
            }
        }
    }
    fun initDespachos(){
        mViewModel.carritoLidaData.observe(this){ state->mAdapter.submitList(state.toMutableList())
        }
        getDespachos()
    }
    fun getDespachos(){
        mViewModel.getCarritoDespacho()
    }
    fun deleteDespacho(carrito: Carrito){
        mViewModel.deleteCarrito(carrito)
    }
    fun editDespacho(carrito: Carrito){
        mViewModel.updateCarritoDespacho(carrito, this)
    }
    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val day = c[Calendar.DAY_OF_MONTH]
        val month = c[Calendar.MONTH]
        val year = c[Calendar.YEAR]
        val picker = DatePickerDialog(
            this,
            { datePicker: DatePicker, mDay: Int, mMonth: Int, mYear: Int ->
                fecha = mDay.toString() + "-" + (mMonth + 1).toString() + "-" + mYear.toString()
                mViewBinding.editTextDate.setText(fecha.toString())
            }, year, month, day
        )
        picker.show()
    }
}