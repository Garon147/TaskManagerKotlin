package orion.garon.taskmanagerk

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.j256.ormlite.misc.TransactionManager
import orion.garon.taskmanagerk.database.HelperFactory
import orion.garon.taskmanagerk.database.Task
import java.sql.SQLException
import java.util.ArrayList
import java.util.concurrent.Callable

/**
 * Created by VKI on 27.04.2018.
 */
class Presenter(context: Context) : AppCompatActivity() {

    var tasks: ArrayList<Task> = ArrayList()

    init {

        HelperFactory.setDatabaseHelper(context)

        try {
            this.tasks = HelperFactory.databaseHelper.getTaskDAO().getAllTasks() as ArrayList<Task>
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun saveTask(taskName: EditText, taskTime: EditText, taskStartDate: EditText,
                 taskDueDate: EditText, taskDescription: EditText) {

        try {

            TransactionManager.callInTransaction(HelperFactory.databaseHelper.getConnectionSource()) {

                Thread(Runnable {
                    try {

                        val task = Task(taskName.text.toString(), 0, Status.NEW.toString(),
                                java.lang.Float.parseFloat(taskTime.text.toString()),
                                taskStartDate.text.toString(),
                                taskDueDate.text.toString(),
                                taskDescription.text.toString())
                        HelperFactory.databaseHelper.getTaskDAO().createOrUpdate(task)

                    } catch (e: SQLException) {

                        e.printStackTrace()
                    }
                }).start()
                null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateTask(selectedTask: Task, taskName: EditText, taskTime: EditText,
                   taskStartDate: EditText, taskDueDate: EditText,
                   taskDescription: EditText, taskState: EditText,
                   taskProgress: EditText) {

        try {

            TransactionManager.callInTransaction(HelperFactory.databaseHelper.getConnectionSource()) {
                Thread(Runnable {
                    try {

                        val task = HelperFactory.databaseHelper.getTaskDAO().getTaskById(selectedTask.id)

                        if (task != null) {
                            task!!.state = taskState.text.toString()
                            task!!.estimatedTime = java.lang.Float.valueOf(taskTime.text.toString())
                            task!!.completion = Integer.valueOf(taskProgress.text.toString())
                            task!!.name = taskName.text.toString()
                            task!!.description = taskDescription.text.toString()
                            task!!.startDate = taskStartDate.text.toString()
                            task!!.dueDate = taskDueDate.text.toString()

                            HelperFactory.databaseHelper.getTaskDAO().update(task)
                        }

                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                }).start()
                null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

}