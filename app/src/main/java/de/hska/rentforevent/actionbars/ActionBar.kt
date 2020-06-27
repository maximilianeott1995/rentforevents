package de.hska.rentforevent.actionbars

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import de.hska.rentforevent.R

open class ActionBar : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            super.onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}