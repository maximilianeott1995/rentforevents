package de.hska.musiconnect.services

import android.content.ContentValues
import android.content.Context
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import de.hska.musiconnect.database.DbService
import de.hska.musiconnect.database.UserContract
import de.hska.musiconnect.helpers.Toaster.showToast
import de.hska.musiconnect.models.Register
import java.lang.Exception

class RegisterService(val context: Context) {
    private val db = DbService(context).writableDatabase

    fun register(registerData: Register): Boolean {
        return try {
            val values = ContentValues().apply {
                put(UserContract.UserEntry.COLUMN_NAME_USERNAME, registerData.username)
                put(UserContract.UserEntry.COLUMN_NAME_EMAIL, registerData.email)
                put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, Hasher.hash(registerData.passsword, HashType.SHA_512))
            }

            val newRowId = db?.insert(UserContract.UserEntry.TABLE_NAME, null, values)
            newRowId != null && newRowId != (-1).toLong()

        } catch (exception: Exception) {
            showToast(exception.message ?: "No exception message", context)
            false
        }
    }
}