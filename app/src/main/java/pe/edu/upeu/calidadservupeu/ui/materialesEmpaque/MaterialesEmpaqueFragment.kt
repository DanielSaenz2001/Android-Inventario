package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque

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
import pe.edu.upeu.calidadservupeu.databinding.MaterialesEmpaqueFragmentBinding
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.ui.base.BaseFragment
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.adapter.MaterialesEmpaqueListAdapter
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.details.MaterialesEmpaqueDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MaterialesEmpaqueFragment : BaseFragment<MaterialesEmpaqueViewModel, MaterialesEmpaqueFragmentBinding>() {

    public override val mViewModel: MaterialesEmpaqueViewModel by viewModels()
    private val mAdapter= MaterialesEmpaqueListAdapter(this::onItemClicked)
    override fun getViewBinding(): MaterialesEmpaqueFragmentBinding = MaterialesEmpaqueFragmentBinding.inflate(layoutInflater)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding.EmpaqueRecyclerView.adapter=mAdapter

        UtilsToken.tipo_actividad = ""

        iniEmpaques()
        return mViewBinding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_camera -> {
                val intent = Intent(this.requireContext(), QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "EMPAQUE")
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

                val materialesEmpaqueFiltro= MaterialesEmpaque()
                materialesEmpaqueFiltro.nombre =newText
                getEmpaquesFiltro(materialesEmpaqueFiltro)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }




    private fun onItemClicked(materialesEmpaque: MaterialesEmpaque, imageView: ImageView, v:View){
        when(v) {
            /*v.btnDelete->{
                deleteProduct(producto)
                Log.i("LLEGA_D", "Si va Eliminar")
            }
*/
            else->{
                val intent= Intent(this.context, MaterialesEmpaqueDetailsActivity::class.java)
                intent.putExtra(MaterialesEmpaqueDetailsActivity.MATERIALESEMPAQUE_ID,materialesEmpaque.id)
                val options= ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this.requireActivity(), imageView, imageView.transitionName
                )
                startActivity(intent, options.toBundle())
            }
        }
    }
    private fun iniEmpaques(){
        mViewModel.empaqueLidaData.observe(this.requireActivity()){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getEmpaques()
            showLoading(false)
        }
        getEmpaques()

    }

    private fun deleteEmpaque(materialesEmpaque: MaterialesEmpaque){
        mViewModel.deleteEmpaqueById(materialesEmpaque)
    }

    private fun addEmpaque(materialesEmpaque: MaterialesEmpaque){
        mViewModel.createEmpaque(materialesEmpaque)
    }

    private fun getEmpaques(){
        mViewModel.getEmpaque()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getEmpaquesFiltro(materialesEmpaque: MaterialesEmpaque){
        mViewModel.getEmpaquesFiltro(materialesEmpaque)
    }
}