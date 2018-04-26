package orion.garon.taskmanagerk.database

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager

/**
 * Created by VKI on 27.04.2018.
 */
class HelperFactory {

    companion object {

        @JvmStatic lateinit var databaseHelper: DatabaseHelper

//        fun getDatabaseHelper(): DatabaseHelper? {
//            return databaseHelper
//        }

        fun setDatabaseHelper(context: Context) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper::class.java)
        }

        fun releaseDatabaseHelper() {
            OpenHelperManager.releaseHelper()
//            databaseHelper = null
        }
    }
}