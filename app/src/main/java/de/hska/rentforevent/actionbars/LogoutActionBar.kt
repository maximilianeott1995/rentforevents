package de.hska.rentforevent.actionbars

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import de.hska.rentforevent.R
import de.hska.rentforevent.activities.LoginActivity

open class LogoutActionBar : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            showLogoutDialog()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout?")
            .setMessage("Bist Du sicher, dass du dich ausloggen willst?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog transparent_button is clicked.
            .setPositiveButton("JA") { _, _ ->
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }

            // A null listener allows the transparent_button to dismiss the dialog and take no further action.
            .setNegativeButton("Abbrechen", null)
            //                .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
            .show()
    }
}