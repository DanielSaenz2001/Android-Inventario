package pe.edu.upeu.calidadservupeu.ui.proveedor.details

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityDetailsProveedorBinding
import pe.edu.upeu.calidadservupeu.model.Proveedores
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProveedorDetailsActivity : BaseActivity<ProveedorDetailsViewModel, ActivityDetailsProveedorBinding>() {

    override val mViewModel: ProveedorDetailsViewModel by viewModels()
    private  lateinit var proveedor: Proveedores
    companion object{
        const val PROVEEDOR_ID="proveedorId"
    }
    override fun getViewBinding(): ActivityDetailsProveedorBinding = ActivityDetailsProveedorBinding.inflate(layoutInflater)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_main, menu)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val proveedorId=intent.extras?.getInt(PROVEEDOR_ID)?: throw IllegalArgumentException("ProveedorId es nulo")

        iniProveedor(proveedorId)
    }

    fun updateProveedor(proveedor: Proveedores){
        mViewModel.updateProveedor(proveedor)
    }
    private fun iniProveedor(id: Int){
        mViewModel.getProveedor(id).observe(this){
                proveedorX->mViewBinding.proveedorContent.apply {
                proveedor = proveedorX
                proveedorName.text=proveedor.nombre
                proveedorRuc.text=proveedor.ruc
        }
            mViewBinding.imageView.load(proveedor.imagen_proveedores){
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
        }
        return super.onOptionsItemSelected(item)
    }
}