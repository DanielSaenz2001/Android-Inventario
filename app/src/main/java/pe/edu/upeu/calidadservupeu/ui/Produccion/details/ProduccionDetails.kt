package pe.edu.upeu.calidadservupeu.ui.Produccion.details


import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_produccion_details.view.*
import kotlinx.android.synthetic.main.activity_productos_despachos_details.view.*
import kotlinx.android.synthetic.main.activity_productos_despachos_details.view.conductor_name_details
import kotlinx.android.synthetic.main.form_details_empaque.*
import kotlinx.android.synthetic.main.form_details_empaque.view.*
import kotlinx.android.synthetic.main.item_producto_despacho_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProduccionDetailsBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.ProduccionDetalles
import pe.edu.upeu.calidadservupeu.ui.Produccion.adapter.ProduccionDetailsListAdapter
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProduccionDetails : BaseActivity<ProduccionDetailsViewModel, ActivityProduccionDetailsBinding>() {

    override val mViewModel: ProduccionDetailsViewModel by viewModels()
    override fun getViewBinding(): ActivityProduccionDetailsBinding = ActivityProduccionDetailsBinding.inflate(layoutInflater)
    private  lateinit var produccion: Produccion
    private val mAdapter= ProduccionDetailsListAdapter(this::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        val produccionId=intent.extras?.getInt(DetailsActivity.PRODUCT_ID)?: throw IllegalArgumentException("DetallesId es nulo")
        mViewBinding.ProductoProduccionDetailsRecyclerView.adapter=mAdapter
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
        supportActionBar?.title = "Producto Elaborado"
        iniDetails(produccionId)
        iniProduccion(produccionId)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                supportFinishAfterTransition()
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
        }
        return super.onOptionsItemSelected(item)
    }
    private fun iniDetails(produccionId:Int){
        mViewModel.produccionLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewModel.getProduccionDetalles(produccionId)

    }
    private fun iniProduccion(produccionId:Int){
        mViewModel.getProduccion(produccionId).observe(this){
            despachox->mViewBinding.pProDetail.apply {
                produccion=despachox
                Producto_Name.text= produccion.nombre_producto
                Cantidad_Producto.text= produccion.cantidad_producto.toString()
                Responsable_Produccion.text= produccion.responsable_nombre
                Fecha_Produccion.text=  produccion.fecha
                Log.i("IMAGEN:",produccion.toString())
                Log.i("IMAGEN2:",produccion.imagen_producto!!)
                mViewBinding.imagenProducto.load(produccion.imagen_producto.toString()){
                    placeholder(R.drawable.ic_photo)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }
    private fun onItemClicked(produccion: ProduccionDetalles, v: View){}
}