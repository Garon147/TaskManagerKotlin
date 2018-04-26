package orion.garon.taskmanagerk

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*

/**
 * Created by VKI on 27.04.2018.
 */
class TaskFragment : Fragment() {

    private var presenter: Presenter? = null

    internal var taskName: EditText? = null
    internal var taskTime: EditText? = null
    internal var taskStartDate: EditText? = null
    internal var taskDueDate: EditText? = null
    internal var taskDescription: EditText? = null
    internal var createButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_task, container, false)
        presenter = Presenter(context)

        taskName = view?.findViewById(R.id.task_name) as EditText
        taskTime = view?.findViewById(R.id.task_time) as EditText
        taskStartDate = view?.findViewById(R.id.task_start) as EditText
        taskDueDate = view?.findViewById(R.id.task_due) as EditText
        taskDescription = view?.findViewById(R.id.task_description) as EditText

        createButton = view?.findViewById(R.id.button_create) as Button
        createButton?.setOnClickListener(View.OnClickListener { v: View? -> onClick(v!!) })

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate : Calendar = Calendar.getInstance()
        orion.garon.taskmanagerk.DatePicker.setDatePicker(context, taskStartDate, taskDueDate, currentDate)
        orion.garon.taskmanagerk.DatePicker.setCallback(taskTime)
    }

    fun onClick(view : View) {

        if (checkInput()) {

            val thread = Thread(Runnable {
                presenter?.saveTask(
                        taskName!!,
                        taskTime!!,
                        taskStartDate!!,
                        taskDueDate!!,
                        taskDescription!!) })
            thread.start()
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    fun checkInput(): Boolean {

        if (
                taskName?.getText().toString().isEmpty() ||
                taskTime?.getText().toString().isEmpty() ||
                taskStartDate?.getText().toString().isEmpty() ||
                        taskDueDate?.getText().toString().isEmpty()) {

            Toast.makeText(context, R.string.input_error_msg, Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }
    }
}