package pe.edu.upeu.calidadservupeu.ui.control

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_control.*
import kotlinx.android.synthetic.main.activity_regulaciones.*
import kotlinx.android.synthetic.main.activity_regulaciones.container
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.ProductosDespachados
import pe.edu.upeu.calidadservupeu.ui.InMateEmpaque.InMateEmpaque
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.InMatePrima
import pe.edu.upeu.calidadservupeu.ui.Produccion.Produccion
import pe.edu.upeu.calidadservupeu.ui.control.ControlInventario.ControlInventarioActivity
import pe.edu.upeu.calidadservupeu.ui.home.HomeFragment
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class Control : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        var colors: IntArray
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            colors = intArrayOf(0xffFF5143.toInt(), 0xff309eae.toInt())
        } else {
            colors = intArrayOf(0xff860A01.toInt(), 0xff00514E.toInt())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors)
        )
        supportActionBar?.title = "Control de inventario"
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activities_main, menu)
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

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            if(UtilsToken.rol_user == "Administrador"){
                return 3
            }
            if(UtilsToken.rol_user == "Jefe de Area"){
                if(UtilsToken.area_user == "Ingreso"){
                    return 2
                }
                if(UtilsToken.area_user == "Jefe"){
                    return 3
                }
                return 1
            }
            return 0
        }
    }
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            if(UtilsToken.rol_user == "Administrador"){
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                    rootView.txtnombreActividad.text="Materias Primas"
                    rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                        intent.putExtra(ControlInventarioActivity.tipo,"prima")
                        startActivity(intent)
                    }
                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                    rootView.txtnombreActividad.text="Materiales de Empaque"
                    rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                        intent.putExtra(ControlInventarioActivity.tipo,"empaque")
                        startActivity(intent)
                    }
                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                    rootView.txtnombreActividad.text="Productos Elaborados"
                    rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                        intent.putExtra(ControlInventarioActivity.tipo,"producto")
                        startActivity(intent)
                    }

                }
            }
            if(UtilsToken.rol_user == "Jefe de Area"){
                if(UtilsToken.area_user == "Ingreso"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"prima")
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"empaque")
                            startActivity(intent)
                        }
                    }
                }
                if(UtilsToken.area_user == "Produccion"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Productos Elaborados"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"producto")
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Ventas"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Productos Elaborados"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"producto")
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Jefe"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"prima")
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"empaque")
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Productos Elaborados"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ControlInventarioActivity::class.java)
                            intent.putExtra(ControlInventarioActivity.tipo,"producto")
                            startActivity(intent)
                        }

                    }
                }
            }
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}