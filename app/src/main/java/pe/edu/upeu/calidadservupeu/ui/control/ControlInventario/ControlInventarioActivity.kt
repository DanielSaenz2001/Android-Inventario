package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_produccion.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityControlInventarioBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.control.Control
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter.ControlListAdapter
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import java.util.*

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ControlInventarioActivity :  BaseActivity<ControlInventarioViewModel, ActivityControlInventarioBinding>()  {

    override val mViewModel: ControlInventarioViewModel by viewModels()
    override fun getViewBinding(): ActivityControlInventarioBinding = ActivityControlInventarioBinding.inflate(layoutInflater)
    companion object{
        const val tipo="tipo"
    }

    private val mAdapter= ControlListAdapter(this::onItemClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        val tipo=intent.extras?.getString(tipo)?: throw IllegalArgumentException("tipo es nulo")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mViewBinding.InventarioRecyclerView.adapter=mAdapter
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        if(tipo=="prima"){
            getPrima()
        }
        if(tipo=="empaque"){
            getEmpaque()
        }
        if(tipo=="producto"){
            getProducto()
        }
        supportActionBar?.title = "Control de inventario"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activities_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(Intent(this, Control::class.java))
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

            R.id.action_create_despacho_producto -> {
                val tipo=intent.extras?.getString(tipo)?: throw IllegalArgumentException("tipo es nulo")
                val c = Calendar.getInstance()
                val day = c[Calendar.DAY_OF_MONTH]
                val month = c[Calendar.MONTH]
                val year = c[Calendar.YEAR]
                var fecha= year.toString() +"-"+ (month+1).toString() + "-"+day.toString()


                val control = ControlInventario()
                if(tipo=="prima"){
                    UtilsToken.inventario="prima"
                    control.fecha_inicio=fecha
                    mViewModel.addPrima(control,this)
                }
                if(tipo=="empaque"){
                    UtilsToken.inventario="empaque"
                    control.fecha_inicio=fecha
                    mViewModel.addEmpaque(control,this)
                }
                if(tipo=="producto"){
                    UtilsToken.inventario="producto"
                    control.fecha_inicio=fecha
                    mViewModel.addProductos(control,this)
                }
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getPrima(){
        mViewModel.inventarioLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getPrimas()
            showLoading(false)
        }
        mViewModel.getPrimas()
    }
    private fun getEmpaque(){
        mViewModel.inventarioLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getEmpaque()
            showLoading(false)
        }
        mViewModel.getEmpaque()
    }
    private fun getProducto(){
        mViewModel.inventarioLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getProductos()
            showLoading(false)
        }
        mViewModel.getProductos()
    }
    private fun onItemClicked(controlInventario:ControlInventario, imageView: ImageView, v: View){
        when(v){
            v.imageView->{
                val intent = Intent(this, ControlInventarioDetails::class.java)
                intent.putExtra(ControlInventarioDetails.id,controlInventario.id)
                startActivity(intent)
            }
        }
    }
}