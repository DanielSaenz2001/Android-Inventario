package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.details

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.items_materias_primas_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityDetailsMateriasPrimasBinding
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MateriasPrimasDetailsActivity : BaseActivity<MateriasPrimasDetailsViewModel, ActivityDetailsMateriasPrimasBinding>() {


    override val mViewModel: MateriasPrimasDetailsViewModel by viewModels()
    private  lateinit var materiasPrimas: MateriasPrimas
    companion object{
        const val MATERIASPRIMAS_ID="materiasprimasId"
    }
    override fun getViewBinding(): ActivityDetailsMateriasPrimasBinding = ActivityDetailsMateriasPrimasBinding.inflate(layoutInflater)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val MateriaId=intent.extras?.getInt(MATERIASPRIMAS_ID)?: throw IllegalArgumentException("MateriaId es nulo")
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Materias Primas"
        iniMateria(MateriaId)
    }
    private fun iniMateria(id: Int){
        mViewModel.getMaterias(id).observe(this){
                materiaX->mViewBinding.materiaContent.apply {
            materiasPrimas = materiaX
            prima_name.text=materiasPrimas.nombre
            prima_origen.text=materiasPrimas.origen
            prima_stock.text=""+materiasPrimas.stock
            prima_unidad.text=materiasPrimas.unidad
            txtTituloPrima.text="Materia Prima"
            txtOrigenPrima.text="Origen"
            txtDetallesPrima.text="Detalles de la Materia Prima"
            txtDetallesStockPrima.text="Stock"
            txtDetallesUnidadPrima.text="Unidad"

        }
            mViewBinding.imageView.load(materiasPrimas.imagen_materias_primas){
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