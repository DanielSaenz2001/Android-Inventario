package pe.edu.upeu.calidadservupeu.ui.Produccion.create

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.form_produccion.*
import kotlinx.android.synthetic.main.form_produccion.view.*
import kotlinx.android.synthetic.main.item_producto_despacho.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProduccionCreateBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.ui.Produccion.Produccion
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.activity.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.CarritoListAdapter
import pe.edu.upeu.calidadservupeu.ui.producto.activity.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import java.util.*


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProduccionCreate : BaseActivity<ProduccionCreateViewModel, ActivityProduccionCreateBinding>() {

    override val mViewModel: ProduccionCreateViewModel by viewModels()
    override fun getViewBinding(): ActivityProduccionCreateBinding = ActivityProduccionCreateBinding.inflate(
        layoutInflater
    )
    private val mAdapter = CarritoListAdapter(this::onItemClicked)
    var fecha: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.ProduccionRecyclerView.adapter = mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Elaborar Producto"
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        initProduccion()
        UtilsToken.tipo_carrito="Produccion"
        mViewBinding.editTextDate.setOnClickListener {
            showDatePickerDialog()
        }
        mViewBinding.btnEditProducto.setOnClickListener {
            UtilsToken.subeventos ="Cambiar Producto"
            val intent = Intent(this, Producto::class.java)
            intent.putExtra(Producto.TIPO_ACTIVIDAD, "Crear_Produccion")
            startActivity(intent)
        }
        mViewBinding.editTextTextPersonName.setText(UtilsToken.nombre_producto)
        val c = Calendar.getInstance()
        val day = c[Calendar.DAY_OF_MONTH]
        val month = c[Calendar.MONTH]
        val year = c[Calendar.YEAR]
        fecha= year.toString() +"-"+ (month+1).toString() + "-"+day.toString()
        mViewBinding.editTextDate.setText(fecha.toString())
        mViewBinding.btnGuardar.setOnClickListener {
            val producionX = pe.edu.upeu.calidadservupeu.model.Produccion()
            producionX.fecha = fecha.toString()
            producionX.cantidad_producto = mViewBinding.editTextTextPersonName2.text.toString().toInt()
            producionX.producto_id = UtilsToken.id_producto
            Log.i("DATOS PROD", producionX.toString())
            if(producionX.producto_id == 0){
                val toast: Toast = Toast.makeText(this,"Seleccione un producto a elaborar", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(producionX.cantidad_producto!! <= 20){
                val toast: Toast = Toast.makeText(this,"El producto a elaborar debe ser mayor a 20", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            if(producionX.producto_id !== 0 && producionX.cantidad_producto!! >= 20){
                Log.i("ESTOY ACA","ASDAS")
                mViewModel.createProduccion(producionX,this)

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_main_operation, menu)
        return true
    }

    private fun onItemClicked(carrito: Carrito, imageView: ImageView, v: View){
        when(v) {
            v.btnDeleteDespacho -> {
                Log.i("Llegara a eliminar", carrito.id.toString())
                deleteProduccion(carrito)
            }

            v.btnEditDespacho -> {
                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_produccion,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)

                val mAlertDialog = mBuild.show()
                mAlertDialog.txtCantidadPrimaProduccion.setText(carrito.cantidad_materias.toString())

                mDialogView.btnGuardarProduccion.setOnClickListener {
                    carrito.cantidad_materias = mDialogView.txtCantidadPrimaProduccion.text.toString().toInt()
                    editProduccion(carrito)
                    mAlertDialog.dismiss()
                }
                mDialogView.btnCancelarProduccion.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
    }
    fun initProduccion(){
        mViewModel.carritoLidaData.observe(this){ state->mAdapter.submitList(state.toMutableList())
        }
        getProduccion()
    }
    fun getProduccion(){
        mViewModel.getCarritoProduccion()
    }
    fun deleteProduccion(carrito: Carrito){
        mViewModel.deleteCarrito(carrito)
    }
    fun editProduccion(carrito: Carrito){
        mViewModel.updateCarritoProduccion(carrito, this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, Produccion::class.java)
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
                UtilsToken.subeventos ="Escoger Prima"
                val intent = Intent(this, MateriasPrimas::class.java)
                intent.putExtra(MateriasPrimas.TIPO_ACTIVIDAD, "Crear_Produccion")
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
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