package pe.edu.upeu.calidadservupeu.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.FragmentHomeBinding
import pe.edu.upeu.calidadservupeu.ui.DespachosProductos.ProductosDespachados
import pe.edu.upeu.calidadservupeu.ui.InMateEmpaque.InMateEmpaque
import pe.edu.upeu.calidadservupeu.ui.InMatePrima.InMatePrima
import pe.edu.upeu.calidadservupeu.ui.Produccion.Produccion
import pe.edu.upeu.calidadservupeu.ui.base.BaseFragment
import pe.edu.upeu.calidadservupeu.ui.control.Control
import pe.edu.upeu.calidadservupeu.ui.qr.QrActivity
import pe.edu.upeu.calidadservupeu.ui.regulaciones.Regulaciones
import pe.edu.upeu.calidadservupeu.utils.UtilsToken


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    public override val mViewModel:HomeViewModel by viewModels()
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(
        layoutInflater
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        mViewBinding.container.adapter = mSectionsPagerAdapter
        return mViewBinding.root
    }
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            if(UtilsToken.rol_user == "Administrador"){
                return 6
            }
            if(UtilsToken.rol_user == "Jefe de Area"){
                if(UtilsToken.area_user == "Ingreso"){
                    return 4
                }
                if(UtilsToken.area_user == "Jefe") {
                    return 6
                }
                return 3
            }
            if(UtilsToken.rol_user == "Jefe de Almacen"){
                if(UtilsToken.area_user == "Ingreso"){
                    return 3
                }
                if(UtilsToken.area_user == "Jefe") {
                    return 5
                }
                return 2
            }
            if(UtilsToken.rol_user == "Empleado"){
                if(UtilsToken.area_user == "Ingreso"){
                    return 2
                }
                if(UtilsToken.area_user == "Jefe") {
                    return 4
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
                    rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                    rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), InMatePrima::class.java)
                        startActivity(intent)
                    }
                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                    rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                    rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                        startActivity(intent)
                    }
                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                    rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                    rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), Produccion::class.java)
                        startActivity(intent)
                    }

                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 4){
                    rootView.txtnombreActividad.text="Ver Venta de Productos"
                    rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                        startActivity(intent)
                    }

                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 5){
                    rootView.txtnombreActividad.text="Regulación de inventarios"
                    rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), Regulaciones::class.java)
                        startActivity(intent)
                    }

                }
                if (requireArguments().getInt(ARG_SECTION_NUMBER) == 6){
                    rootView.txtnombreActividad.text="Control de inventario"
                    rootView.imagen.load("https://i.ibb.co/bWBTpCy/control.png"){
                        placeholder(R.drawable.ic_photo)
                        error(R.drawable.ic_broken_image)
                    }
                    rootView.midView.setOnClickListener {
                        val intent = Intent(this.requireContext(), Control::class.java)
                        startActivity(intent)
                    }

                }
            }
            if(UtilsToken.rol_user == "Jefe de Area"){
                if(UtilsToken.area_user == "Ingreso"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        rootView.txtnombreActividad.text="Control de inventario"
                        rootView.imagen.load("https://i.ibb.co/bWBTpCy/control.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Control::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Produccion"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Control de inventario"
                        rootView.imagen.load("https://i.ibb.co/bWBTpCy/control.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Control::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Ventas"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Control de inventario"
                        rootView.imagen.load("https://i.ibb.co/bWBTpCy/control.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Control::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Jefe"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        rootView.txtnombreActividad.text="Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 5){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 6){
                        rootView.txtnombreActividad.text="Control de inventario"
                        rootView.imagen.load("https://i.ibb.co/bWBTpCy/control.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Control::class.java)
                            startActivity(intent)
                        }

                    }

                }
            }
            if(UtilsToken.rol_user == "Jefe de Almacen"){
                if(UtilsToken.area_user == "Ingreso"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Produccion"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Ventas"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Jefe"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3){
                        rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 4){
                        rootView.txtnombreActividad.text="Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 5){
                        rootView.txtnombreActividad.text="Regulación de inventarios"
                        rootView.imagen.load("https://i.ibb.co/BcpQF6v/regulacion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Regulaciones::class.java)
                            startActivity(intent)
                        }

                    }
                }
            }
            if(UtilsToken.rol_user == "Empleado"){
                if(UtilsToken.area_user == "Ingreso"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2){
                        rootView.txtnombreActividad.text="Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                }
                if(UtilsToken.area_user == "Produccion"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Ventas"){
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1){
                        rootView.txtnombreActividad.text="Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png"){
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                }
                if(UtilsToken.area_user == "Jefe") {
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                        rootView.txtnombreActividad.text =
                            "Ver Control de ingreso de Materias Primas"
                        rootView.imagen.load("https://i.ibb.co/hYhgtzX/primas.png") {
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMatePrima::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                        rootView.txtnombreActividad.text =
                            "Ver Control de ingreso de Materiales de Empaque"
                        rootView.imagen.load("https://i.ibb.co/VYqHxGY/empaques.png") {
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), InMateEmpaque::class.java)
                            startActivity(intent)
                        }
                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                        rootView.txtnombreActividad.text = "Ver Productos a Elaborar"
                        rootView.imagen.load("https://i.ibb.co/KWstJps/elaboracion.png") {
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent = Intent(this.requireContext(), Produccion::class.java)
                            startActivity(intent)
                        }

                    }
                    if (requireArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                        rootView.txtnombreActividad.text = "Ver Venta de Productos"
                        rootView.imagen.load("https://i.ibb.co/kQyfTTW/ventas.png") {
                            placeholder(R.drawable.ic_photo)
                            error(R.drawable.ic_broken_image)
                        }
                        rootView.midView.setOnClickListener {
                            val intent =
                                Intent(this.requireContext(), ProductosDespachados::class.java)
                            startActivity(intent)
                        }

                    }
                }
            }

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"
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