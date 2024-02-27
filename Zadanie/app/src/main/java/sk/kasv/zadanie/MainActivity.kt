package sk.kasv.zadanie

import DatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sk.kasv.zadanie.ui.screens.DiskUsageScreen
import sk.kasv.zadanie.ui.screens.readUninstalledApps
import sk.kasv.zadanie.ui.theme.ZadanieTheme1
import java.io.File

data class AppInfo(val name: String, val size: Long, val packageName: String)



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZadanieTheme1 {
                val navController = rememberNavController()
                val context = applicationContext

                val totalDiskSpace = getStorageDetails(context).totalSize
                val appInfoList = getInstalledAppsInfo(context)
//                val allInstalledApps = getAllInstalledApps(context) // Retrieve all installed apps

                NavHost(navController, startDestination = "optimizationScreen") {
                    composable("optimizationScreen") {
                        OptimizationScreen(navController)
                    }
                    composable(
                        route = "batteryScreen/{batteryPercentage}/{remainingTime}",
                        arguments = listOf(
                            navArgument("batteryPercentage") { type = NavType.IntType },
                            navArgument("remainingTime") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val batteryPercentage =
                            backStackEntry.arguments?.getInt("batteryPercentage") ?: 0
                        val remainingTime =
                            backStackEntry.arguments?.getString("remainingTime") ?: ""
                        BatteryScreen()
                    }
                    composable("resultsScreen") {
                        ResultsScreen()
                    }
                    composable("networkScreen") {
                        NetworkScreen()
                    }
                    composable("diskScreen") {
                        DiskUsageScreen(totalDiskSpace, appInfoList) { context, packageName ->
                            uninstallApp(context, packageName)
                        }
                    }
                }
            }
        }
    }

    private fun uninstallApp(context: Context, packageName: String) {
        Log.d("UninstallApp", "Attempting to uninstall package: $packageName")

        val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE).apply {
            data = Uri.parse("package:$packageName")
            putExtra(Intent.EXTRA_RETURN_RESULT, true)
        }

        try {
            context.startActivity(intent)
            Log.d("UninstallApp", "Uninstall intent started successfully.")
        } catch (e: Exception) {
            Log.e("UninstallApp", "Error starting uninstall intent: ${e.message}")
        }
    }


//    private fun uninstallApp(context: Context, packageName: String) {
//        val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE).apply {
//            data = Uri.parse("package:$packageName")
//            putExtra(Intent.EXTRA_RETURN_RESULT, true)
//        }
//        context.startActivity(intent)
//    }

    private fun saveAppInfoToDB(context: Context, appInfo: AppInfo) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_NAME, appInfo.name)
            put(AppInfoContract.AppInfoEntry.COLUMN_NAME_APP_SIZE, appInfo.size)
            put(AppInfoContract.AppInfoEntry.COLUMN_NAME_PACKAGE_NAME, appInfo.packageName)
        }

        // Insert the new row
        db.insert(AppInfoContract.AppInfoEntry.TABLE_NAME, null, values)
    }

    @Composable
    fun ResultsScreen() {
        val context = LocalContext.current
        val uninstalledApps = remember { readUninstalledApps(context) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Uninstalled Apps",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display app information
            for (appInfo in uninstalledApps) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = appInfo.name)
                    Text(text = "${appInfo.size} bytes") // Adjust the formatting as needed
                    Text(text = appInfo.packageName)
                }
            }
        }
    }



    @Composable
    fun Greeting(name: String) {
        Text(
            text = "Hello $name!",
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    fun TopAppsBySizeScreen(context: Context) {
        val packageManager = context.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Exclude system apps

        val sortedApps = apps.sortedByDescending { appInfo ->
            try {
                val packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0)
                packageInfo.applicationInfo.sourceDir.let { sourceDir ->
                    File(sourceDir).length()
                }
            } catch (e: PackageManager.NameNotFoundException) {
                0 // Handle exceptions if any
            }
        }

        Column {
            Text(text = "Top 10 Apps by Size", style = MaterialTheme.typography.headlineLarge)

            for (i in 0 until minOf(sortedApps.size, 10)) {
                val appInfo = sortedApps[i]
                val packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0)
                val size = File(packageInfo.applicationInfo.sourceDir).length()

                Text(
                    text = "${i + 1}. ${appInfo.loadLabel(packageManager)} - ${
                        Formatter.formatFileSize(
                            context,
                            size
                        )
                    }",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun getStorageDetails(context: Context): StorageInfo {
        val storagePath = Environment.getExternalStorageDirectory().path
        val stat = StatFs(storagePath)
        val blockSizeLong = stat.blockSizeLong
        val totalBlocksLong = stat.blockCountLong
        val availableBlocksLong = stat.availableBlocksLong

        val totalSize = blockSizeLong * totalBlocksLong
        val availableSize = blockSizeLong * availableBlocksLong

        return StorageInfo(
            totalSize = Formatter.formatFileSize(context, totalSize),
            availableSize = Formatter.formatFileSize(context, availableSize)
        )
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun getInstalledAppsInfo(context: Context): List<AppInfo> {
//        val packageManager = context.packageManager
//        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
//            .mapNotNull { appInfo ->
//                try {
//                    val packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0)
//                    val appName = appInfo.loadLabel(packageManager).toString()
//                    val appSize = File(packageInfo.applicationInfo.sourceDir).length()
//                    AppInfo(appName, appSize, appInfo.packageName) // Corrected to Long type
//                } catch (e: PackageManager.NameNotFoundException) {
//                    null // Ignore apps that can't be found
//                }
//            }
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getInstalledAppsInfo(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(0)

        return installedApps
            .filter { appInfo ->
                (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 && (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0
            }
            .mapNotNull { appInfo ->
                try {
                    val packageInfo = packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS)
                    val requestedPermissions = packageInfo.requestedPermissions
                    val hasNetworkPermission = requestedPermissions?.contains(android.Manifest.permission.INTERNET) ?: false
                    val hasStoragePermission = requestedPermissions?.contains(android.Manifest.permission.READ_EXTERNAL_STORAGE) ?: false

                    if (!hasNetworkPermission && !hasStoragePermission) {
                        val appName = appInfo.loadLabel(packageManager).toString()
                        val appSize = File(packageInfo.applicationInfo.sourceDir).length()
                        AppInfo(appName, appSize, appInfo.packageName)
                    } else {
                        null
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    null // Ignore apps that can't be found
                }
            }
    }

    fun getAllInstalledApps(context: Context): List<String> {
        val packageManager = context.packageManager
        val installedApps = ArrayList<String>()

        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in apps) {
            val appName = appInfo.loadLabel(packageManager).toString()
            installedApps.add(appName)
        }

        return installedApps
    }


    data class StorageInfo(val totalSize: String, val availableSize: String)

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ZadanieTheme1 {
            Greeting("Android")
        }
    }

}
