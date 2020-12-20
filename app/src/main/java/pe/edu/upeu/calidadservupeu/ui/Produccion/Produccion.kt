package pe.edu.upeu.calidadservupeu.ui.Produccion

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
import pe.edu.upeu.calidadservupeu.databinding.ActivityProduccionBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.ui.Produccion.adapter.ProduccionListAdapter
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.details.ProduccionDetails
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class Produccion :  BaseActivity<ProduccionViewModel, ActivityProduccionBinding>() {
    override val mViewModel: ProduccionViewModel by viewModels()
    override fun getViewBinding(): ActivityProduccionBinding = ActivityProduccionBinding.inflate(layoutInflater)

    private val mAdapter= ProduccionListAdapter(this::onItemClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.ProductoProduccionRecyclerView.adapter=mAdapter
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
        supportActionBar?.title = "Productos Elaborados"
        iniProduccion()
    }
    private fun onItemClicked(produccion: Produccion, imageView: ImageView, v: View){
        when(v){
            v.btnVerProduccion->{
                val intent = Intent(this, ProduccionDetails::class.java)
                intent.putExtra(DetailsActivity.PRODUCT_ID,produccion.id)
                startActivity(intent)
            }
        }
    }
    private fun iniProduccion(){
        mViewModel.produccionLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getProduccion()
            showLoading(false)
        }
        getProduccion()

    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getProduccion(){
        mViewModel.getProduccion()
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
                val intent = Intent(this, ProduccionCreate::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}