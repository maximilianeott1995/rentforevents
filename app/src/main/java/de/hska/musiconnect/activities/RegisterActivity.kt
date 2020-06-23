package de.hska.musiconnect.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import de.hska.musiconnect.R
import de.hska.musiconnect.actionbars.ActionBar
import de.hska.musiconnect.helpers.Toaster.showToast
import de.hska.musiconnect.models.Register
import de.hska.musiconnect.services.RegisterService
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterActivity : ActionBar(), CoroutineScope by MainScope() {
    private lateinit var registerService: RegisterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(x_register_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        registerService = RegisterService(this)
    }

    fun onRegister(view: View) {
        if (x_register_user.text.isBlank() ||
            x_register_email.text.isBlank() ||
            x_register_password.text.isBlank() ||
            x_register_confirm_password.text.isBlank()
        ) {

            showToast("Bitte fülle alle Felder aus!", this)
            return
        }

        if (x_register_password.text.toString() != x_register_confirm_password.text.toString()) {
            showToast("Passwörter stimmen nicht überein!", this)
            return
        }

        showProgress(true)
        launch(Dispatchers.Default) {
            if (registerService.register(
                    Register(
                        x_register_user.text.toString().trim(),
                        x_register_email.text.toString().trim(),
                        x_register_password.text.toString().trim()
                    )
                )
            ) {
                runOnUiThread {
                    showProgress(false)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            } else {
                showToast("Username oder E-Mail bereits vergeben. ", applicationContext)
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            if (show) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                x_register_progress.visibility = View.VISIBLE
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                x_register_progress.visibility = View.GONE
            }

            val shortAnimTime =
                resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            x_register_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        x_register_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            x_register_progress.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}
