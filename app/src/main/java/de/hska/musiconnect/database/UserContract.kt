package de.hska.musiconnect.database

import android.provider.BaseColumns

object UserContract {
    // Table contents are grouped together in an anonymous object.
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "User"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD = "password"
    }
}