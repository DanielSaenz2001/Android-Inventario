package pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege


import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_produccion_details.view.*
import kotlinx.android.synthetic.main.activity_regulaciones_list_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityRegulacionesListBinding
import pe.edu.upeu.calidadservupeu.databinding.ActivityRegulacionesListDetailsBinding
import pe.edu.upeu.calidadservupeu.model.Produccion
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint

class RegulacionesListDetails :  BaseActivity<RegulacionesListViewModel, ActivityRegulacionesListDetailsBinding>()  {
    companion object{
        const val id="id"
    }
    override val mViewModel: RegulacionesListViewModel by viewModels()
    private  lateinit var regulacion: Regulaciones
    override fun getViewBinding(): ActivityRegulacionesListDetailsBinding = ActivityRegulacionesListDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id=intent.extras?.getInt(id)?: throw IllegalArgumentException("DetallesId es nulo")
        iniRegulacion(id)
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Regulación de inventario"
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
    private fun iniRegulacion(id:Int){
        mViewModel.getRegulacion(id).observe(this){
                regulacionX->mViewBinding.pProDetail.apply {
            regulacion=regulacionX
            fecha_regulacion.text= regulacion.fecha
            actividad_regulacion.text= regulacion.actividad
            producto_regulacion.text = regulacion.producto_nombre
            responsable_regulacion.text= regulacion.responsable
            cantidad_regulacion.text= regulacion.cantidad.toString()
            motivo_regulacion.text= regulacion.motivo
            mViewBinding.imagenProducto.load(regulacion.imagen_producto.toString()){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }
        }
    }
}