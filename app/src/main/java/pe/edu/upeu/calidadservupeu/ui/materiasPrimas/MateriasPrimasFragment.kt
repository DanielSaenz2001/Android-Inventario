package pe.edu.upeu.calidadservupeu.ui.materiasPrimas


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
import pe.edu.upeu.calidadservupeu.databinding.MateriasPrimasFragmentBinding
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.base.BaseFragment
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.MateriasPrimasListAdapter
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.details.MateriasPrimasDetailsActivity
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MateriasPrimasFragment : BaseFragment<MateriasPrimasViewModel, MateriasPrimasFragmentBinding>() {

    public override val mViewModel: MateriasPrimasViewModel by viewModels()
    private val mAdapter = MateriasPrimasListAdapter(this::onItemClicked)
    override fun getViewBinding(): MateriasPrimasFragmentBinding =
        MateriasPrimasFragmentBinding.inflate(layoutInflater)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding.materiasPrimasRecyclerView.adapter = mAdapter

        UtilsToken.tipo_actividad = ""

        iniMaterias()
        return mViewBinding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_camera -> {
                val intent = Intent(this.requireContext()!!, QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "PRIMAL")
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

                val materiasPrimasFiltro = MateriasPrimas()
                materiasPrimasFiltro.nombre =newText
                getMateriasFiltro(materiasPrimasFiltro)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun onItemClicked(materiasPrimas: MateriasPrimas, imageView: ImageView, v:View){
        when(v!!) {
            /*v.btnDelete->{
                deleteProduct(producto)
                Log.i("LLEGA_D", "Si va Eliminar")
            }
*/
            else->{
                val intent= Intent(this.context, MateriasPrimasDetailsActivity::class.java)
                intent.putExtra(MateriasPrimasDetailsActivity.MATERIASPRIMAS_ID,materiasPrimas.id)
                val options= ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this.requireActivity(), imageView, imageView.transitionName
                )
                startActivity(intent, options.toBundle())
            }
        }
    }
    private fun iniMaterias(){
        mViewModel.materiasLidaData.observe(this.requireActivity()){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getMaterias()
            showLoading(false)
        }
        getMaterias()

    }

    private fun deleteMateria(materiasPrimas: MateriasPrimas){
        mViewModel.deleteMateriaById(materiasPrimas)
    }

    private fun addMaterias(materiasPrimas: MateriasPrimas){
        mViewModel.createMateria(materiasPrimas)
    }

    private fun getMaterias(){
        mViewModel.getMaterias()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun getMateriasFiltro(materiasPrimas: MateriasPrimas){
        mViewModel.getMateriasFiltro(materiasPrimas)
    }

}