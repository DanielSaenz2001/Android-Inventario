package pe.edu.upeu.calidadservupeu.ui.proveedor

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
import pe.edu.upeu.calidadservupeu.databinding.FragmentProveedorBinding
import pe.edu.upeu.calidadservupeu.model.Proveedores
import pe.edu.upeu.calidadservupeu.ui.base.BaseFragment
import pe.edu.upeu.calidadservupeu.ui.proveedor.adapter.ProveedorListAdapter
import pe.edu.upeu.calidadservupeu.ui.proveedor.details.ProveedorDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class ProveedorFragment : BaseFragment<ProveedorViewModel, FragmentProveedorBinding>() {

    public override val mViewModel: ProveedorViewModel by viewModels()
    private val mAdapter= ProveedorListAdapter(this::onItemClicked)
    override fun getViewBinding(): FragmentProveedorBinding = FragmentProveedorBinding.inflate(
        layoutInflater
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding.proveedorRecyclerView.adapter=mAdapter

        iniProveedores()


        return mViewBinding.root

    }

    private fun onItemClicked(proveedor: Proveedores, imageView: ImageView, v: View){
        when(v){
            /*v.btnDelete->{
                deleteProduct(producto)
                Log.i("LLEGA_D", "Si va Eliminar")
            }
*/
            else->{
                val intent= Intent(this.context, ProveedorDetailsActivity::class.java)
                intent.putExtra(ProveedorDetailsActivity.PROVEEDOR_ID, proveedor.id)
                val options= ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this.requireActivity(), imageView, imageView.transitionName
                )
                startActivity(intent, options.toBundle())
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_camera -> {
                val intent = Intent(this.requireContext(), QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "PROVENDER")
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

                val proveedorFiltro = Proveedores()
                proveedorFiltro.ruc = newText
                getProveedoresFiltro(proveedorFiltro)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }



    private fun iniProveedores(){
        mViewModel.proveedoresLidaData.observe(this.requireActivity()){ state->mAdapter.submitList(
            state.toMutableList()
        )

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getProveedores()
            showLoading(false)
        }
        getProveedores()

    }

    private fun deleteProveedor(proveedor: Proveedores){
        mViewModel.deleteProveedorById(proveedor)
    }

    private fun addProveedores(proveedor: Proveedores){
        mViewModel.createProveedor(proveedor)
    }

    private fun getProveedores(){
        mViewModel.getProveedores()
    }
    private fun showLoading(isLoading: Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getProveedoresFiltro(proveedor: Proveedores){
        mViewModel.getProveedoresFiltro(proveedor)
    }
}