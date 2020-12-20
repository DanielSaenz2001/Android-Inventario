package pe.edu.upeu.calidadservupeu.ui.login

import android.content.Intent
import pe.edu.upeu.calidadservupeu.utils.UtilsToken
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pe.edu.upeu.calidadservupeu.ui.main.MainActivity
import pe.edu.upeu.calidadservupeu.databinding.ActivityLoginBinding
import pe.edu.upeu.calidadservupeu.model.Login
import pe.edu.upeu.calidadservupeu.ui.base.BaseActivity

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@InternalCoroutinesApi
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override val mViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = Intent(this, MainActivity::class.java)
        mViewBinding.login.setOnClickListener {
            val userX=Login()
            userX.email = mViewBinding.username.text.toString()
            userX.password = mViewBinding.password.text.toString()
            mViewModel.loginUser(userX,this)
        }




    }
    private fun goHome(){
        Log.i("token",UtilsToken.TOKEN_CONTENT)

        startActivity(Intent(this, MainActivity::class.java))
    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_main, menu)
        return true
    }*/
    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
}


