package de.hska.musiconnect.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import de.hska.musiconnect.R
import de.hska.musiconnect.helpers.Toaster.showToast
import de.hska.musiconnect.models.Login
import de.hska.musiconnect.services.LoginService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(x_login_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        loginService = LoginService(this)
    }

    fun onLogin(view: View) {
        if(x_email.text.isBlank() || x_password.text.isBlank()) {
            showToast("Bitte fülle alle Felder aus!", this)
            return
        }

        showProgress(true)
        launch(Dispatchers.Default) {
            if(loginService.login(Login(x_email.text.toString().trim(), x_password.text.toString().trim()))) {
                runOnUiThread {
                    showProgress(false)
                    startActivity(Intent(applicationContext,  MainActivity::class.java))
                }
            } else {
                runOnUiThread {
                    showProgress(false)
                    showToast("Username oder Passwort falsch!", applicationContext)
                }
            }
        }
    }

    fun onPasswordReset(view: View) {
        // Nicht Teil der Implementierungsaufgabe
    }

    fun onRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }


    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            if (show) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                x_login_progress.visibility = VISIBLE
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                x_login_progress.visibility = GONE
            }

            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            x_login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        x_login_progress.visibility = if (show) VISIBLE else GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            x_login_progress.visibility = if (show) VISIBLE else GONE
        }
    }
}
