package pe.edu.upeu.calidadservupeu.ui.control.ControlInventario

import android.app.AlertDialog
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_control_inventario_details.view.*
import kotlinx.android.synthetic.main.activity_regulaciones_list_details.*
import kotlinx.android.synthetic.main.form_control_inventario.*
import kotlinx.android.synthetic.main.form_control_inventario.view.*
import kotlinx.android.synthetic.main.item_inventario.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityControlInventarioDetailsBinding
import pe.edu.upeu.calidadservupeu.model.ControlInventario
import pe.edu.upeu.calidadservupeu.model.ControlInventarioDetalles
import pe.edu.upeu.calidadservupeu.model.Regulaciones
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter.ControlCreateListAdapter
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.adapter.ControlDetailsListAdapter

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ControlInventarioDetails:  BaseActivity<ControlInventarioViewModel, ActivityControlInventarioDetailsBinding>()  {
//
    companion object{
        const val id="id"
    }
    private val mAdapter = ControlDetailsListAdapter(this::onItemClicked)
    override val mViewModel: ControlInventarioViewModel by viewModels()
    private  lateinit var controlInventario: ControlInventario
    override fun getViewBinding(): ActivityControlInventarioDetailsBinding = ActivityControlInventarioDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.InventarioRecyclerView.adapter = mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id=intent.extras?.getInt(id)?: throw IllegalArgumentException("DetallesId es nulo")
        iniControl(id)
        iniInventario(id)
        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Control de inventario"
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
    private fun iniControl(id:Int){
        mViewModel.getControl(id).observe(this){
                controlInventarioX->mViewBinding.pProDetail.apply {
                controlInventario=controlInventarioX
                fecha_inicio_inventario.text= controlInventario.fecha_inicio
                fecha_fin_inventario.text= controlInventario.fecha_fin
                responsable_inventario.text= controlInventario.responsable
                tipo_inventario.text= controlInventario.tipo
                estado_inventario.text= controlInventario.estado
                mViewBinding.imagenProducto.load(controlInventario.imagen_user.toString()){
                    placeholder(R.drawable.ic_photo)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }
    fun iniInventario(id:Int){
        mViewModel.controlLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())
        }
        mViewModel.getInventarioList(id)
    }
    private fun onItemClicked(control: ControlInventarioDetalles, imageView: ImageView, v: View){
        Log.i("1",v.toString())
        Log.i("2",imageView.toString())
        Log.i("3",control.toString())
    }

}