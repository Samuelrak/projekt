// AppUsage.kt
package sk.kasv.zadanie

data class AppUsage(val name: String, val sizeGB: Long)

fun convertToAppUsage(appInfo: AppInfo): AppUsage {
    return AppUsage(appInfo.name, appInfo.size / 1024) // Converting bytes to GB
}