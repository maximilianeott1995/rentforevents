package de.hska.musiconnect.database

import android.provider.BaseColumns

object Scripts {
    const val SQL_CREATE_USER_TABLE =
        "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${UserContract.UserEntry.COLUMN_NAME_USERNAME} TEXT unique," +
                "${UserContract.UserEntry.COLUMN_NAME_EMAIL} TEXT unique," +
                "${UserContract.UserEntry.COLUMN_NAME_PASSWORD} TEXT)"

    const val SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${UserContract.UserEntry.TABLE_NAME}"

}