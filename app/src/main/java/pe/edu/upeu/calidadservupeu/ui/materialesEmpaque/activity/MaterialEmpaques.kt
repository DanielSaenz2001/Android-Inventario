package pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.activity

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
import kotlinx.android.synthetic.main.item_materiales_embarque.view.*
import kotlinx.android.synthetic.main.item_materias_primas.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityMaterialEmpaquesBinding
import pe.edu.upeu.calidadservupeu.databinding.ActivityMateriasPrimasBinding
import pe.edu.upeu.calidadservupeu.model.Carrito
import pe.edu.upeu.calidadservupeu.model.MaterialesEmpaque
import pe.edu.upeu.calidadservupeu.model.Producto
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.create.ProductosDespachosCreate
import pe.edu.upeu.calidadservupeu.ui.Produccion.create.ProduccionCreate
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.MaterialesEmpaqueViewModel
import pe.edu.upeu.calidadservupeu.ui.materialesEmpaque.adapter.MaterialesEmpaqueListAdapter
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.MateriasPrimasViewModel
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.activity.MateriasPrimas
import pe.edu.upeu.calidadservupeu.ui.materiasPrimas.adapter.MateriasPrimasListAdapter
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Rege.RegulacionesCreate
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MaterialEmpaques : BaseActivity<MaterialesEmpaqueViewModel, ActivityMaterialEmpaquesBinding>() {
    public override val mViewModel: MaterialesEmpaqueViewModel by viewModels()
    private val mAdapter= MaterialesEmpaqueListAdapter(this::onItemClicked)
    override fun getViewBinding(): ActivityMaterialEmpaquesBinding = ActivityMaterialEmpaquesBinding.inflate(layoutInflater)

    companion object{
        const val TIPO_ACTIVIDAD="tipo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.MaterialEmpaquesRecyclerView.adapter=mAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val Tipo_Actividad=intent.extras?.getString(TIPO_ACTIVIDAD)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
        UtilsToken.tipo_actividad = Tipo_Actividad
        supportActionBar?.title = "Materiales de Empaque"
        iniProducts()


    }

    private fun iniProducts(){
        mViewModel.empaqueLidaData.observe(this){
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
                val Tipo_Actividad=intent.extras?.getString(MateriasPrimas.TIPO_ACTIVIDAD)?: throw IllegalArgumentException("TIPO_BUSQUEDA es nulo")
                val intent = Intent(this, QrActivity::class.java)
                intent.putExtra(QrActivity.TIPO_BUSQUEDA, "EMPAQUE")
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
                val empaqueFiltro= MaterialesEmpaque()
                empaqueFiltro.nombre =newText
                getUsersFiltro(empaqueFiltro)
                return false
            }
        })
        return true
    }

    private fun getMaterias(){
        mViewModel.getEmpaque()
    }
    private fun showLoading(isLoading:Boolean){
        mViewBinding.swipeRefreshLayout.isRefreshing=isLoading
    }
    private fun onItemClicked(materialEmpaques: MaterialesEmpaque, imageView: ImageView, v: View){
        when(v!!){
            v.btnSelectEmbarque->{
                selectEmpaque(materialEmpaques)
            }
        }
    }
    private fun getUsersFiltro(materialEmpaques: MaterialesEmpaque){
        mViewModel.getEmpaquesFiltro(materialEmpaques)
    }
    private fun selectEmpaque(materialEmpaques: MaterialesEmpaque){

        if(UtilsToken.tipo_actividad == "Crear_Despacho_Producto"){
            val carritoX= Carrito()
            carritoX.id = UtilsToken.id_carrito.toInt()
            carritoX.empaque_id=materialEmpaques.id
            carritoX.cantidad_empaque=1
            mViewModel.updateCarritoDespacho(carritoX)
            val intent = Intent(this, ProductosDespachosCreate::class.java)
            startActivity(intent)
        }
        if(UtilsToken.tipo_actividad =="Regulaciones"){
            val carritoX=Carrito()
            carritoX.empaque_id=materialEmpaques.id
            mViewModel.createCarritoRegulacion(carritoX,this)
            val intent = Intent(this, RegulacionesCreate::class.java)
            intent.putExtra(RegulacionesCreate.tipo,"empaque")
            startActivity(intent)
        }
    }
}