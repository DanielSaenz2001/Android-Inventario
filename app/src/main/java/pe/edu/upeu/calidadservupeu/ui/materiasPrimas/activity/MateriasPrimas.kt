package pe.edu.upeu.calidadservupeu.ui.materiasPrimas.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_materias_primas.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityMateriasPrimasBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.MateriasPrimasViewModel
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.CarritoListAdapter
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import pe.edu.upeu.calidadservupeu.model.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create.ProductosDespachosCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.MateriasPrimasListAdapter
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.RegulacionesCreate

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MateriasPrimas : BaseActivity<MateriasPrimasViewModel, ActivityMateriasPrimasBinding>() {
    public override val mViewModel: MateriasPrimasViewModel by viewModels()
    private val mAdapter= MateriasPrimasListAdapter(this::onItemClicked)

    companion object{
        const val TIPO_ACTIVIDAD="tipo"
    }

    override fun getViewBinding(): ActivityMateriasPrimasBinding = ActivityMateriasPrimasBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.MateriasPrimasRecyclerView.adapter=mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val Tipo_Actividad=intent.extras?.getString(TIPO_ACTIVIDAD)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
        UtilsToken.tipo_actividad = Tipo_Actividad
        Log.i("Tipo_ACtividad", Tipo_Actividad)
        supportActionBar?.title = "Materias Primas"
        iniProducts()


    }

    private fun iniProducts(){
        mViewModel.materiasLidaData.observe(this){
                state->mAdapter.submitList(state.toMutableList())

        }
        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getMaterias()
            showLoading(false)
        }
        getMaterias()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home->{
                supportFinishAfterTransition()
                return true
            }
            R.id.action_camera -> {
                val Tipo_Actividad=intent.extras?.getString(TIPO_ACTIVIDAD)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
                val intent = Intent(this, QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "PRIMAL")
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

                val materiaFiltro= MateriasPrimas()
                materiaFiltro.nombre =newText
                getUsersFiltro(materiaFiltro)
                return false
            }
        })
        return true
    }

    private fun getMaterias(){
        mViewModel.getMaterias()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun onItemClicked(materiasPrimas: MateriasPrimas, imageView: ImageView, v: View){
        when(v!!){
            v.btnSelectMateriasPrimas->{
                selectProducto(materiasPrimas)
            }
        }
    }
    private fun getUsersFiltro(materiasPrimas: MateriasPrimas){
        mViewModel.getMateriasFiltro(materiasPrimas)
    }
    private fun selectProducto(materiasPrimas: MateriasPrimas){
        Log.i("dato",materiasPrimas.toString())

        if(UtilsToken.tipo_actividad == "Crear_Produccion"){
            val carritoX= Carrito()
            carritoX.materias_primas_id=materiasPrimas.id
            mViewModel.createCarritoProduccion(carritoX,this)
            val intent = Intent(this, ProduccionCreate::class.java)
            startActivity(intent)
        }

        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX=Carrito()
            carritoX.materias_primas_id=materiasPrimas.id
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"prima")
            startActivity(intent)
        }
    }
}