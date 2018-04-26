package orion.garon.taskmanagerk.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

/**
 * Created by VKI on 27.04.2018.
 */
private val DATABASE_NAME = "mydb.db"
private val DATABASE_VERSION = 1


class DatabaseHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TAG = DatabaseHelper::class.java.simpleName

    private var taskDAO: TaskDAO? = null

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {

        try {

            TableUtils.createTable<Task>(connectionSource, Task::class.java)
        } catch (e: SQLException) {

            Log.e(TAG, "Error creating DB " + DATABASE_NAME)
            throw RuntimeException(e)
        }

    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {

        try {

            TableUtils.dropTable<Task, Any>(connectionSource, Task::class.java, true)
            onCreate(database, connectionSource)
        } catch (e: SQLException) {
            Log.e(TAG, "Error upgrading db $DATABASE_NAME from ver $DATABASE_VERSION")
            throw RuntimeException(e)
        }

    }

    override fun close() {
        super.close()
        taskDAO = null
    }

    @Throws(SQLException::class)
    fun getTaskDAO(): TaskDAO {

        if (taskDAO == null) {
            taskDAO = TaskDAO(getConnectionSource(), Task::class.java)
        }

        return taskDAO!!
    }
}