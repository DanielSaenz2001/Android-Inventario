package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.details

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.items_materiales_embarque_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityDetailsMaterialesEmpaqueBinding
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MaterialesEmpaqueDetailsActivity : BaseActivity<MateriasEmpaqueDetailsViewModel, ActivityDetailsMaterialesEmpaqueBinding>() {


    override val mViewModel: MateriasEmpaqueDetailsViewModel by viewModels()
    private  lateinit var materialesEmpaque: MaterialesEmpaque
    companion object{
        const val MATERIALESEMPAQUE_ID="materialesEmpaqueId"
    }
    override fun getViewBinding(): ActivityDetailsMaterialesEmpaqueBinding = ActivityDetailsMaterialesEmpaqueBinding.inflate(layoutInflater)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val EmpaqueId=intent.extras?.getInt(MATERIALESEMPAQUE_ID)?: throw IllegalArgumentException("materialesEmpaqueId es nulo")
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Materiales de Empaque"
        iniEmpaque(EmpaqueId)
    }

    private fun iniEmpaque(id: Int){
        mViewModel.getEmpaque(id).observe(this){
            empaqueX->mViewBinding.empaqueContent.apply {
                materialesEmpaque = empaqueX
                empaqueName.text=materialesEmpaque.nombre
                empaqueStock.text=""+materialesEmpaque.stock
                empaquePeso.text= materialesEmpaque.peso_neto.toString() + "| | | "+ materialesEmpaque.peso_bruto.toString()
                empaqueMedida.text=""+materialesEmpaque.medidas

                txtTituloEmpaque.text="Material de Empaque"
                txtMedidasEmpaque.text="Medidas"
                txtPesoEmpaque.text="Peso Neto y Bruto"
                txtDetallesEmpaque.text="Detalles del Material de Empaque"
                txtDetallesStockEmpaque.text="Stock"

            }
            mViewBinding.imageView.load(materialesEmpaque.imagen_material_empaques){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }
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
}