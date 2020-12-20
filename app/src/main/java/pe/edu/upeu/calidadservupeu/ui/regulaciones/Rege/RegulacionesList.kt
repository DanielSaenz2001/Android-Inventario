package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_produccion.view.*
import kotlinx.android.synthetic.main.item_produccion.view.imageView
import kotlinx.android.synthetic.main.item_regulaciones.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProduccionBinding
import pe.edu.upeu.calidadservupeu.databinding.ActivityRegulacionesListBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.Produccion.ProduccionViewModel
import pe.edu.upeu.calidadservupeu.ui.Produccion.adapter.ProduccionListAdapter
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.details.ProduccionDetails
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.adapter.RegulacionesListAdapter
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RegulacionesList :  BaseActivity<RegulacionesListViewModel, ActivityRegulacionesListBinding>() {

    override val mViewModel: RegulacionesListViewModel by viewModels()
    override fun getViewBinding(): ActivityRegulacionesListBinding = ActivityRegulacionesListBinding.inflate(layoutInflater)
    companion object{
        const val tipo="tipo"
    }
    private val mAdapter= RegulacionesListAdapter(this::onItemClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        val tipo=intent.extras?.getString(tipo)?: throw IllegalArgumentException("DetallesId es nulo")
        mViewBinding.RegulacionesRecyclerView.adapter=mAdapter
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
        if(tipo=="prima"){
            getPrima()
            supportActionBar?.title = "Materia prima"
        }
        if(tipo=="empaque"){
            getEmpaque()
            supportActionBar?.title = "Materiales de empaque"
        }
        if(tipo=="producto"){
            getProducto()
            supportActionBar?.title = "Productos elaborados"
        }
    }
    private fun onItemClicked(regulaciones: Regulaciones, imageView: ImageView, v: View){
        when(v){
            v.imageView->{
                Log.i("DATOS",regulaciones.id.toString())
                val intent = Intent(this, RegulacionesListDetails::class.java)
                intent.putExtra(RegulacionesListDetails.id,regulaciones.id)
                startActivity(intent)
            }
        }
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getPrima(){
        mViewModel.regulacionesLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getPrimas()
            showLoading(false)
        }
        mViewModel.getPrimas()
    }
    private fun getEmpaque(){
        mViewModel.regulacionesLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getEmpaque()
            showLoading(false)
        }
        mViewModel.getEmpaque()
    }
    private fun getProducto(){
        mViewModel.regulacionesLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getProductos()
            showLoading(false)
        }
        mViewModel.getProductos()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activities_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(Intent(this, pe.edu.upeu.calidadservupeu.ui.regulaciones.Regulaciones::class.java))
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
                val tipo=intent.extras?.getString(tipo)?: throw IllegalArgumentException("DetallesId es nulo")
                val intent = Intent(this, RegulacionesCreate::class.java)
                if(tipo=="prima"){
                    intent.putExtra(RegulacionesCreate.tipo,"prima")
                }
                if(tipo=="empaque"){
                    intent.putExtra(RegulacionesCreate.tipo,"empaque")
                }
                if(tipo=="producto"){
                    intent.putExtra(RegulacionesCreate.tipo,"producto")
                }
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}