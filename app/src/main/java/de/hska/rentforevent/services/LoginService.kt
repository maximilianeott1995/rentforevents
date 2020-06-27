package de.hska.rentforevent.services

import android.content.Context
import android.provider.BaseColumns
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import de.hska.rentforevent.database.DbService
import de.hska.rentforevent.database.UserContract
import de.hska.rentforevent.helpers.Toaster.showToast
import de.hska.rentforevent.models.Login

class LoginService(private val context: Context) {
    private val db = DbService(context).readableDatabase

    fun login(loginData: Login): Boolean {
        try {
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            val projection =
                arrayOf(
                    BaseColumns._ID,
                    UserContract.UserEntry.COLUMN_NAME_EMAIL,
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD
                )

            // Filter results WHERE "title" = 'My Title'
            val selection = "${UserContract.UserEntry.COLUMN_NAME_EMAIL} = ?"
            val selectionArgs = arrayOf(loginData.email)

            // How you want the results sorted in the resulting Cursor
            val sortOrder = "${UserContract.UserEntry.COLUMN_NAME_EMAIL} DESC"

            val cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            with(cursor) {
                while (moveToNext()) {
                    val password = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PASSWORD))
                    return password == Hasher.hash(loginData.password, HashType.SHA_512)
                }
            }

            return false
        } catch (exception: Exception) {
            showToast(exception.message ?: "no exception", context)
            return false
        }
    }
}