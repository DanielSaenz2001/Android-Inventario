package pe.edu.upeu.calidadservupeu.ui.producto.details

import android.app.AlertDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.content.res.Configuration
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.items_product_details.*
import kotlinx.android.synthetic.main.items_product_details.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityDetailsBinding
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsActivity : BaseActivity<ProductDetailsViewModel, ActivityDetailsBinding>() {

    override val mViewModel: ProductDetailsViewModel by viewModels()
    private  lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val productoId=intent.extras?.getInt(PRODUCT_ID)?: throw IllegalArgumentException("ProdictId es nulo")
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



        iniProducts(productoId)
    }


    private fun iniProducts(productoId:Int){


        mViewModel.getProduct(productoId).observe(this){
            productx->mViewBinding.productContent.apply {
            producto=productx
            Log.i("PRODUCTO:", producto.toString())
            product_name.text=producto.nombre
            product_preci.text="$/ "+producto.precio_total
            product_modelo.text=producto.modelo
            product_stock.text=""+producto.stock
            product_unidad.text=""+producto.unidad
            txtTituloProducto.text="Producto"
            txtModeloProducto.text="Modelo"
            txtPrecioProducto.text="Precio"
            txtCompraProducto.text="Detalles del producto"
            txtCompraStockProducto.text="Stock"
            txtCompraUnidadProducto.text="Unidad"
        }
            mViewBinding.imageView.load(producto.imagen_producto){
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }
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
    private fun updateProduct(producto: Producto){
        mViewModel.updateProduct(producto)
    }
    override fun getViewBinding(): ActivityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)


    companion object{
        const val PRODUCT_ID="productId"
    }



}