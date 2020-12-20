package pe.edu.upeu.calidadservupeu.ui.producto


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ProductoFragmentBinding
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.base.BaseFragment
import pe.edu.upeu.calidadservupeu.ui.producto.details.DetailsActivity
import pe.edu.upeu.calidadservupeu.ui.producto.adapter.ProductoListAdapter
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class ProductoFragment : BaseFragment<ProductoViewModel, ProductoFragmentBinding>() {
    public override val mViewModel:ProductoViewModel by viewModels()
    private val mAdapter=ProductoListAdapter(this::onItemClicked)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding.productosRecyclerView.adapter=mAdapter
        UtilsToken.tipo_actividad = ""
        iniProducts()
        return mViewBinding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_camera -> {
                val intent = Intent(this.requireContext()!!, QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "PRODUCT")
                startActivity(intent)
            }else ->return false
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = menuItem.actionView as SearchView

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
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun getViewBinding(): ProductoFragmentBinding=ProductoFragmentBinding.inflate(layoutInflater)

    private fun onItemClicked(producto: Producto, imageView: ImageView, v:View){
        when(v){
            else->{
                val intent=Intent(this.context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.PRODUCT_ID,producto.id)
                val options=ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this.requireActivity(), imageView, imageView.transitionName
                )
                startActivity(intent, options.toBundle())
            }
        }
    }


    private fun iniProducts(){
        mViewModel.productosLidaData.observe(this.requireActivity()){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getProductos()
            showLoading(false)
        }
        getProductos()

    }

    private fun deleteProduct(producto: Producto){
        mViewModel.deleteProductById(producto)
    }

    private fun addProduct(producto: Producto){
        mViewModel.createProduct(producto)
    }

    private fun getProductos(){
        mViewModel.getProductos()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getUsersFiltro(producto: Producto){
        mViewModel.getProductosFiltro(producto)
    }

}