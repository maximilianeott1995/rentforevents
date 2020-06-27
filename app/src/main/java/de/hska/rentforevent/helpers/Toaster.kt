package de.hska.rentforevent.helpers

import android.content.Context
import android.widget.Toast

object Toaster {

    fun showToast(text: String, applicationContext: Context) {
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
}