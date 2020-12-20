package pe.edu.upeu.calidadservupeu.ui.DespachosProductos.details


import android.app.AlertDialog
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
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_productos_despachos_details.view.*
import kotlinx.android.synthetic.main.form_details_empaque.*
import kotlinx.android.synthetic.main.form_details_empaque.view.*
import kotlinx.android.synthetic.main.item_producto_despacho_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProductosDespachosDetailsBinding
import pe.edu.upeu.calidadservupeu.model.ProductDespachos
import pe.edu.upeu.calidadservupeu.model.ProductosDespachosDetalles
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.adapter.DespachosDetailsListAdapter
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductosDespachosDetails : BaseActivity<ProductosDespachosDetailsViewModel, ActivityProductosDespachosDetailsBinding>() {

    override val mViewModel: ProductosDespachosDetailsViewModel by viewModels()
    override fun getViewBinding(): ActivityProductosDespachosDetailsBinding = ActivityProductosDespachosDetailsBinding.inflate(layoutInflater)
    private val mAdapter= DespachosDetailsListAdapter(this::onItemClicked)
    private  lateinit var despacho: ProductDespachos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.ProductoDespachoDetailsRecyclerView.adapter=mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val detallesId=intent.extras?.getInt(DetailsActivity.PRODUCT_ID)?: throw IllegalArgumentException("DetallesId es nulo")
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Venta de los Producto"
        iniDetails(detallesId)
        iniDespachos(detallesId)
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
    private fun iniDetails(despachoId:Int){
        mViewModel.despachosLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewModel.getDespachosDetalles(despachoId)

    }
    private fun iniDespachos(despachoId:Int){
        mViewModel.getDespachos(despachoId).observe(this){
                despachox->mViewBinding.pDesDetail.apply {
                despacho=despachox
                conductor_name_details.text= despacho.nombreConductor
                fecha_salida_details.text= despacho.fecha
                ciudad_destino_details.text= despacho.ciudadDestino
                vehiculo_details.text= despacho.vehiculo
                responsable_details.text= despacho.responsable_nombre
            }
        }
    }
    private fun onItemClicked(despachos: ProductosDespachosDetalles, imageView: ImageView, v: View){
        when(v){
            v.btnVerEmpaque-> {
                Log.i("ASD",despachos.toString())
                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_details_empaque,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)
                val mAlertDialog = mBuild.show()

                mAlertDialog.imageViewDetailsEmpaque.load(despachos.empaque_imagen.toString()){
                    placeholder(R.drawable.ic_photo)
                    error(R.drawable.ic_broken_image)
                }

                mAlertDialog.txtCantidadDespacho.text = despachos.cantidad_empaque.toString()
                mAlertDialog.txtDespachoProductoName.text = despachos.empaque_nombre.toString()
                mDialogView.btnCerrarForm.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
    }
}