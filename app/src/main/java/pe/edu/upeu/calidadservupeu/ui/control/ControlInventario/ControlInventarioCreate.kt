package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.form_control_inventario.*
import kotlinx.android.synthetic.main.form_control_inventario.view.*
import kotlinx.android.synthetic.main.item_inventario.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityControlInventarioCreateBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.ProductosDespachados
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter.ControlCreateListAdapter
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.activity.MaterialEmpaques
import pe.edu.upeu.calidadservupeu.ui.producto.activity.Producto
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ControlInventarioCreate : BaseActivity<ControlInventarioCreateViewModel, ActivityControlInventarioCreateBinding>() {

    //
    private val mAdapter = ControlCreateListAdapter(this::onItemClicked)
    var fecha: String? = null
    override val mViewModel: ControlInventarioCreateViewModel by viewModels()
    override fun getViewBinding(): ActivityControlInventarioCreateBinding = ActivityControlInventarioCreateBinding.inflate(
        layoutInflater
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.InventarioRecyclerView.adapter = mAdapter
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
        val inventarioId=intent.extras?.getInt(INVENTARIO_ID)?: throw IllegalArgumentException("Id es nulo")
        Log.i("ID:",inventarioId.toString())
        supportActionBar?.title = "Control de inventario"
        iniInventario(inventarioId)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_main_operation, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if(UtilsToken.inventario=="prima"){
                    val intent = Intent(this, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"prima")
                    startActivity(intent)
                }
                if(UtilsToken.inventario=="empaque"){
                    val intent = Intent(this, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"empaque")
                    startActivity(intent)
                }
                if(UtilsToken.inventario=="producto"){
                    val intent = Intent(this, ControlInventarioActivity::class.java)
                    intent.putExtra(ControlInventarioActivity.tipo,"producto")
                    startActivity(intent)
                }

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
            R.id.action_producto_add->{
                val inventarioId=intent.extras?.getInt(INVENTARIO_ID)?: throw IllegalArgumentException("Id es nulo")
                mViewModel.finControl(inventarioId,this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onItemClicked(control: ControlInventarioDetalles, imageView: ImageView, v: View){
        when(v) {
            v.btnSelectInventario -> {
                Log.i("Llegara a eliminar", control.id.toString())
                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_control_inventario,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)

                val mAlertDialog = mBuild.show()
                mAlertDialog.DescripcionInventario.text = control.descripcion.toString()
                mDialogView.btnGuardarInventario.setOnClickListener {
                    control.observacion=mAlertDialog.txtObservacionInvestigacion.text.toString()
                    Log.i("Control:", control.toString())
                    mViewModel.update(control.id!!,control)
                    mAlertDialog.dismiss()
                    val inventarioId=intent.extras?.getInt(INVENTARIO_ID)?: throw IllegalArgumentException("Id es nulo")
                    iniInventario(inventarioId)
                }
                mDialogView.btnCancelarInventario.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }

        }
    }
    fun iniInventario(id:Int){
        mViewModel.controlLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())
        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getInventarioList(id)
            showLoading(false)
        }
        mViewModel.getInventarioList(id)
    }
    companion object{
        const val INVENTARIO_ID="productId"
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
}