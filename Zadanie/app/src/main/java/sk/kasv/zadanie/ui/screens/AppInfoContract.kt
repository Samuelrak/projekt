// AppInfoContract.kt
import android.provider.BaseColumns

object AppInfoContract {
    // Inner class that defines the table contents
    object AppInfoEntry : BaseColumns {
        const val TABLE_NAME = "app_info"
        const val COLUMN_NAME_PACKAGE_NAME = "package_name"
        const val COLUMN_NAME_APP_NAME = "app_name"
        const val COLUMN_NAME_APP_SIZE = "app_size"
        const val _ID = BaseColumns._ID
    }
}