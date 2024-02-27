package sk.kasv.zadanie.ui.screens

import DatabaseHelper
import android.content.ContentValues
import android.content.Context
import sk.kasv.zadanie.AppInfo
data class AppInfo(val name: String, val size: Long, val packageName: String)

fun saveUninstalledAppInfo(context: Context, packageName: String, appName: String, appSize: Long) {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.writableDatabase

    val values = ContentValues().apply {
        put(AppInfoContract.AppInfoEntry.COLUMN_NAME_PACKAGE_NAME, packageName)
        put(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_NAME, appName)
        put(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE, appSize)
    }

    // Insert the new row, returning the primary key value of the new row
    val newRowId = db.insert(AppInfoContract.AppInfoEntry.TABLE_NAME, null, values)
}
fun readUninstalledApps(context: Context): List<AppInfo> {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.readableDatabase

    val projection = arrayOf(
        AppInfoContract.AppInfoEntry.COLUMN_NAME_PACKAGE_NAME,
        AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_NAME,
        AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE
    )

    val sortOrder = "${AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE} DESC"

    val cursor = db.query(
        AppInfoContract.AppInfoEntry.TABLE_NAME,
        projection,
        null,
        null,
        null,
        null,
        sortOrder
    )

    val appInfoList = mutableListOf<AppInfo>()

    while (cursor.moveToNext()) {
        val packageName =
            cursor.getString(cursor.getColumnIndexOrThrow(AppInfoContract.AppInfoEntry.COLUMN_NAME_PACKAGE_NAME))
        val appName =
            cursor.getString(cursor.getColumnIndexOrThrow(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_NAME))
        val appSize =
            cursor.getLong(cursor.getColumnIndexOrThrow(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE))

        appInfoList.add(AppInfo(appName, appSize, packageName))
    }

    cursor.close()
    return appInfoList
}