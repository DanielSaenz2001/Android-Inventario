package pe.edu.upeu.calidadservupeu.ui.InMatePrima.details


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
import kotlinx.android.synthetic.main.activity_in_mate_prima_details.*
import kotlinx.android.synthetic.main.form_details_empaque.*
import kotlinx.android.synthetic.main.form_details_empaque.txtCantidadDespacho
import kotlinx.android.synthetic.main.form_details_empaque.txtDespachoProductoName
import kotlinx.android.synthetic.main.form_details_empaque.view.*
import kotlinx.android.synthetic.main.form_details_empaque.view.btnCerrarForm
import kotlinx.android.synthetic.main.form_details_in_prima.*
import kotlinx.android.synthetic.main.form_details_in_prima.view.*
import kotlinx.android.synthetic.main.item_int_mate_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityInMatePrimaDetailsBinding
import pe.edu.upeu.calidadservupeu.model.IntMate
import pe.edu.upeu.calidadservupeu.model.IntMateDetalles
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.adapter.InMateDetailsListAdapter
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InMatePrimaDetails : BaseActivity<InMatePrimaDetailsViewModel, ActivityInMatePrimaDetailsBinding>()  {

    override val mViewModel: InMatePrimaDetailsViewModel by viewModels()
    override fun getViewBinding(): ActivityInMatePrimaDetailsBinding = ActivityInMatePrimaDetailsBinding.inflate(layoutInflater)
    private val mAdapter= InMateDetailsListAdapter(this::onItemClicked)
    private  lateinit var ingreso: IntMate
    companion object{
        const val IntMate_ID="intMateId"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        UtilsToken.ingreso="prima"
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mViewBinding.IngresosDetailsRecyclerView.adapter=mAdapter
        val id=intent.extras?.getInt(IntMate_ID)?: throw IllegalArgumentException("DetallesId es nulo")
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Ingreso de materias primas"
        iniDetails(id)
        iniIngreso(id)
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
    private fun iniDetails(id:Int){
        mViewModel.ingresosLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewModel.getDetalles(id)

    }
    private fun iniIngreso(id:Int){
        mViewModel.getIngresos(id).observe(this){
                ingresoX->mViewBinding.pDesDetail.apply {
            ingreso=ingresoX
            fecha_entrada.text= ingreso.fecha
            numero_factura.text= ingreso.nFactura
            proveedor_name.text= ingreso.proveedor
            responsable_name.text= ingreso.recibe
            observacion_details.text= ingreso.observacion
            mViewBinding.imagenProveedor.load(ingreso.proveedor_imagen.toString()){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }
        }
    }
    private fun onItemClicked(ingreso: IntMateDetalles, v: View){
        when(v){
            v.btnDetalles-> {
                Log.i("ASD",ingreso.toString())

                val mDialogView = LayoutInflater.from(this).inflate(
                    R.layout.form_details_in_prima,
                    null
                )
                val mBuild = AlertDialog.Builder(this).setView(mDialogView)
                val mAlertDialog = mBuild.show()

                mAlertDialog.txtPlagas.text = "Ausente de Plagas"
                mAlertDialog.txtMateriasExtranas.text = "Ausente de Materiales Extraños"
                if (ingreso.plagas == 1){
                    mAlertDialog.txtPlagas.text = "Existente de Plagas"
                }
                if (ingreso.materias_extranas == 1){
                    mAlertDialog.txtMateriasExtranas.text = "Existente de Materiales Extraños"
                }
                mAlertDialog.txtIntegridad.text = ingreso.integridad.toString()
                mDialogView.btnCerrarPrimaForm.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }

        }
    }
}