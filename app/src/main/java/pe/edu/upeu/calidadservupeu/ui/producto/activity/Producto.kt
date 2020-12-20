package pe.edu.upeu.calidadservupeu.ui.producto.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_producto.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityProductoBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create.ProductosDespachosCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.producto.ProductoViewModel
import pe.edu.upeu.calidadservupeu.ui.producto.adapter.ProductoListAdapter
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.RegulacionesCreate
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.RegulacionesList
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class Producto: BaseActivity<ProductoViewModel, ActivityProductoBinding>() {

    public override val mViewModel: ProductoViewModel by viewModels()
    private val mAdapter= ProductoListAdapter(this::onItemClicked)


    override fun getViewBinding(): ActivityProductoBinding = ActivityProductoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.productosRecyclerView.adapter=mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val Tipo_Actividad=intent.extras?.getString(TIPO_ACTIVIDAD)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
        Log.i("Tipo_ACtividad", Tipo_Actividad)
        UtilsToken.tipo_actividad = Tipo_Actividad
        supportActionBar?.title = "Productos Elaborados"
        iniProducts()


    }
    companion object{
        const val TIPO_ACTIVIDAD="tipo"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home->{
                supportFinishAfterTransition()
                return true
            }
            R.id.action_camera -> {
                val intent = Intent(this, QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "PRODUCT")
                // intent.putExtra(QrActivity.TIPO_ACTIVIDAD, Tipo_Actividad)
                startActivity(intent)
            }else ->return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                val productoFiltro= Producto()
                productoFiltro.nombre =newText
                getUsersFiltro(productoFiltro)
                return false
            }
        })
        return true
    }
    private fun iniProducts(){
        mViewModel.productosLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getProductos()
            showLoading(false)
        }
        getProductos()

    }
    private fun getProductos(){
        mViewModel.getProductos()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun onItemClicked(producto: Producto, imageView: ImageView, v: View){
        when(v){
            v.btnSelectProducto->{
                selectProducto(producto)
            }
        }
    }
    private fun getUsersFiltro(producto: Producto){
        mViewModel.getProductosFiltro(producto)
    }
    private fun selectProducto(producto: Producto){
        if(UtilsToken.tipo_actividad == "Crear_Produccion"){
            UtilsToken.id_producto=producto.id!!.toInt()
            UtilsToken.nombre_producto= producto.nombre.toString()
            val intent = Intent(this, ProduccionCreate::class.java)
            startActivity(intent)
        }
        if(UtilsToken.tipo_actividad =="Crear_Despacho_Producto"){
            val carritoX=Carrito()
            carritoX.producto_id=producto.id
            mViewModel.createCarritoDespacho(carritoX,this)
            val intent = Intent(this, ProductosDespachosCreate::class.java)
            startActivity(intent)
        }

        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX=Carrito()
            carritoX.producto_id=producto.id
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"producto")
            startActivity(intent)
        }
    }
}