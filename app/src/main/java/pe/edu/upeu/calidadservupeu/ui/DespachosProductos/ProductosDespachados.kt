package pe.edu.upeu.calidadservupeu.ui.DespachosProductos


import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_despacho.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProductosDespachadosBinding
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.adapter.DespachosListAdapter
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create.ProductosDespachosCreate
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.details.ProductosDespachosDetails
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductosDespachados : BaseActivity<ProductosDespachosViewModel, ActivityProductosDespachadosBinding>() {

    override val mViewModel: ProductosDespachosViewModel by viewModels()
    override fun getViewBinding(): ActivityProductosDespachadosBinding = ActivityProductosDespachadosBinding.inflate(
        layoutInflater
    )

    private val mAdapter= DespachosListAdapter(this::onItemClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.ProductoDespachoRecyclerView.adapter=mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Ventas de Productos"
        iniDespachos()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activities_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
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
                val intent = Intent(this, ProductosDespachosCreate::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun iniDespachos(){
        mViewModel.despachosLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getDespachos()
            showLoading(false)
        }
        getDespachos()

    }
    private fun getDespachos(){
        mViewModel.getDespachos()
    }
    private fun onItemClicked(despachos: ProductDespachos, v: View){
        when(v){
            v.btnVerDespacho->{
                val intent=Intent(this, ProductosDespachosDetails::class.java)
                intent.putExtra(DetailsActivity.PRODUCT_ID,despachos.id)
                startActivity(intent)
            }
        }
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
}