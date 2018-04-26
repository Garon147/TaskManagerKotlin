package orion.garon.taskmanagerk.database

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException

/**
 * Created by VKI on 27.04.2018.
 */
class TaskDAO(connectionSource : ConnectionSource, dataClass: Class<Task>) :
        BaseDaoImpl<Task, Integer>(connectionSource, dataClass) {

    @Throws(SQLException::class)
    fun getAllTasks(): List<Task> {
        return this.queryForAll()
    }

    @Throws(SQLException::class)
    fun getTaskById(id: Int): Task {
        return this.queryForId(java.lang.Integer(id))
    }
}