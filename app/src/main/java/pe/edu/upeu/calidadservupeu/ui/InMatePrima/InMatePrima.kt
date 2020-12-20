package pe.edu.upeu.calidadservupeu.ui.InMatePrima

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import androidx.core.graphics.toColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityInMatePrimaBinding
import pe.edu.upeu.calidadservupeu.model.IntMate
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.adapter.InMateListAdapter
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.details.InMatePrimaDetails
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.utils.isNight
import java.util.*

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InMatePrima : BaseActivity<InMatePrimaViewModel, ActivityInMatePrimaBinding>(){

    override val mViewModel: InMatePrimaViewModel by viewModels()
    override fun getViewBinding(): ActivityInMatePrimaBinding = ActivityInMatePrimaBinding.inflate(
        layoutInflater
    )
    private val mAdapter= InMateListAdapter(this::onItemClicked)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.IngresoMaterialesRecyclerView.adapter=mAdapter
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
        supportActionBar?.title = "Ingreso de materias primas"
        iniIngreso()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_main, menu)
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
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun iniIngreso(){
        mViewModel.ingresosLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getIngreso()
            showLoading(false)
        }
        getIngreso()

    }
    private fun getIngreso(){
        mViewModel.getIngresos()
    }
    private fun onItemClicked(inmate: IntMate,imageView: ImageView, v: View){
        when(v){
            else->{
                val intent=Intent(this, InMatePrimaDetails::class.java)
                intent.putExtra(InMatePrimaDetails.IntMate_ID,inmate.id)
                startActivity(intent)
            }
        }
    }
}