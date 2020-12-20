package pe.edu.upeu.calidadservupeu.ui.main

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.api.load
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.form_cambiar_contra.view.*
import kotlinx.android.synthetic.main.form_cambiar_imagen.*
import kotlinx.android.synthetic.main.form_cambiar_imagen.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.R
import pe.edu.upeu.calidadservupeu.databinding.ActivityMainBinding
import pe.edu.upeu.calidadservupeu.model.User
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity
import pe.edu.upeu.calidadservupeu.utils.UtilsToken

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@InternalCoroutinesApi
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var  navView: NavigationView
    lateinit var  drawerLayout: DrawerLayout


    override val mViewModel: MainViewModel by viewModels()
    //private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setSupportActionBar(toolbar)

        drawerLayout = mViewBinding.drawerLayout
        navView = mViewBinding.navView

        navView.setNavigationItemSelectedListener {
            menuItem -> drawerLayout.closeDrawers()
            false
        }
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_producto,
                R.id.nav_materias,
                R.id.nav_embarque,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.name_user) as TextView
        val navEmail = headerView.findViewById<View>(R.id.email_user) as TextView
        val navImage = headerView.findViewById<ImageView>(R.id.logito) as ImageView
        navImage.load(UtilsToken.imagen_user)
        navUsername.text = UtilsToken.name_user + " | " + UtilsToken.rol_user
        navEmail.text = UtilsToken.email_user


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
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
                R.id.action_cambiar_imagen -> {
                    val user = User()
                    val mDialogView = LayoutInflater.from(this).inflate(
                        R.layout.form_cambiar_imagen,
                        null
                    )
                    val mBuild = AlertDialog.Builder(this).setView(mDialogView)
                    val mAlertDialog = mBuild.show()
                    mAlertDialog.txtImagenUsuario.setText(UtilsToken.imagen_user)

                    mDialogView.btnGuardarImagen.setOnClickListener {
                        user.imagen_user = mDialogView.txtImagenUsuario.text.toString()
                        cambiarImagen(user)
                        mAlertDialog.dismiss()
                        val toast: Toast = Toast.makeText(
                            this,
                            "La imagen ha sido cambiada",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                        toast.show()

                    }
                    mDialogView.btnCancelarImagen.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
                    return true
                }
                R.id.action_cambiar -> {
                    val user = User()
                    val mDialogView = LayoutInflater.from(this).inflate(
                        R.layout.form_cambiar_contra,
                        null
                    )
                    val mBuild = AlertDialog.Builder(this).setView(mDialogView)

                    val mAlertDialog = mBuild.show()
                    mDialogView.btnGuardarContra.setOnClickListener {
                        user.email = mDialogView.txtEmail.text.toString()
                        user.password = mDialogView.txtContraAnti.text.toString()
                        user.password_new = mDialogView.txtContraNue.text.toString()
                        user.password_confirmation = mDialogView.txtContraNueConf.text.toString()
                        Log.i("datos", user.toString())
                        if (user.password_new == user.password_confirmation) {
                            cambiarcontraseña(user)
                            mAlertDialog.dismiss()

                        } else {
                            val toast: Toast = Toast.makeText(
                                this,
                                "Las contraseñas no coinciden",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                            toast.show()
                        }

                    }
                    mDialogView.btnCancelarContra.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
                    return true
                }
                R.id.logout_sistem -> {
                    mViewModel.logout(UtilsToken.TOKEN_CONTENT, this)
                    return true
                }else ->return false
        }
        return super.onOptionsItemSelected(item)
    }
    fun cambiarcontraseña(user: User){
        mViewModel.cambiarContra(UtilsToken.TOKEN_CONTENT, user, this)
    }
    fun cambiarImagen(user: User){
        mViewModel.cambiarImagen(UtilsToken.TOKEN_CONTENT, user, this)
    }
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}