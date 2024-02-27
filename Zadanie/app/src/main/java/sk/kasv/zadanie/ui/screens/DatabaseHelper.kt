import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This method is used to upgrade the database schema if needed.
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AppInfo.db"

        // Use val instead of const val for SQL_CREATE_ENTRIES
        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${AppInfoContract.AppInfoEntry.TABLE_NAME} (" +
                    "${AppInfoContract.AppInfoEntry._ID} INTEGER PRIMARY KEY," +
                    "${AppInfoContract.AppInfoEntry.COLUMN_NAME_PACKAGE_NAME} TEXT," +
                    "${AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_NAME} TEXT," +
                    "${AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE} INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${AppInfoContract.AppInfoEntry.TABLE_NAME}"
    }
}
