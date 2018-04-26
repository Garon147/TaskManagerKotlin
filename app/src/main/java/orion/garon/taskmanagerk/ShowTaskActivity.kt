package orion.garon.taskmanagerk

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.*
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import orion.garon.taskmanagerk.database.Constants
import orion.garon.taskmanagerk.database.Task
import java.util.*

/**
 * Created by VKI on 26.04.2018.
 */
class ShowTaskActivity : AppCompatActivity() {

    private var selectedTask: Task? = null
    private var textFields: ArrayList<EditText>? = null
    private var currentDate: Calendar? = null
    private var presenter: Presenter? = null
    private var context: Context? = null


    internal var taskName: EditText? = null
    internal var taskTime: EditText? = null
    internal var taskStartDate: EditText? = null
    internal var taskDueDate: EditText? = null
    internal var taskDescription: EditText? = null
    internal var createButton: Button? = null
    internal var taskProgress: EditText? = null
    internal var taskState: EditText? = null
    internal var contentEdit: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_task)
        ButterKnife.bind(this)
        presenter = Presenter(this)

//        selectedTask = Task()
        val intent : Intent = getIntent()
        context = this
        currentDate = Calendar.getInstance()


        taskName = findViewById(R.id.task_name) as EditText
        taskTime = findViewById(R.id.task_time) as EditText
        taskStartDate = findViewById(R.id.task_start) as EditText
        taskDueDate = findViewById(R.id.task_due) as EditText
        taskDescription = findViewById(R.id.task_description) as EditText
        createButton = findViewById(R.id.button_create) as Button
        taskProgress = findViewById(R.id.task_progress) as EditText
        taskState = findViewById(R.id.task_state) as EditText
        contentEdit = findViewById(R.id.content_edit) as LinearLayout

        initTask(intent)
        initViews()
        setListeners()
        groupTextFields()
    }

    fun initTask(intent: Intent) {

        selectedTask = Task(
                intent.getStringExtra(Constants.NAME),
                intent.getIntExtra(Constants.COMPLETION, 0),
                intent.getStringExtra(Constants.STATE),
                intent.getFloatExtra(Constants.ESTIMATED_TIME, 0f),
                intent.getStringExtra(Constants.START_DATE),
                intent.getStringExtra(Constants.DUE_DATE),
                intent.getStringExtra(Constants.DESCRIPTION)
                )
        selectedTask?.id = intent.getIntExtra(Constants.ID, 0)
    }

    fun initViews() {

        contentEdit?.setVisibility(View.VISIBLE)
        createButton?.setText(R.string.save_changes_button)
        createButton?.setVisibility(View.GONE)
        createButton?.setOnClickListener(View.OnClickListener { v: View? -> onClick(v!!) })

        taskName?.setText(selectedTask?.name)
        taskTime?.setText(selectedTask?.estimatedTime.toString())
        taskDescription?.setText(selectedTask?.description)
        taskDueDate?.setText(selectedTask?.dueDate)
        taskStartDate?.setText(selectedTask?.startDate)
        taskProgress?.setText(selectedTask?.completion.toString())
        taskState?.setText(selectedTask?.state)

        orion.garon.taskmanagerk.DatePicker.setCallback(taskTime)
        taskProgress?.setLongClickable(false)
        taskState?.setLongClickable(false)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        taskProgress?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus -> })
    }

    fun setListeners() {

        orion.garon.taskmanagerk.DatePicker.setDatePicker(context, taskStartDate, taskDueDate, currentDate)
        taskState?.setOnClickListener(View.OnClickListener {
            val dialog = orion.garon.taskmanagerk.DatePicker.createDialog(context, taskState)
            dialog.show()
        })
    }

    fun groupTextFields() {

        textFields = ArrayList()

        textFields!!.add(taskName!!)
        textFields!!.add(taskTime!!)
        textFields!!.add(taskDescription!!)
        textFields!!.add(taskDueDate!!)
        textFields!!.add(taskStartDate!!)
        textFields!!.add(taskState!!)
        textFields!!.add(taskProgress!!)

        for (i in textFields!!.indices) {
            textFields!!.get(i).isEnabled = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_show_task, menu)

        val toggleservice = menu?.findItem(R.id.toggle_test)
        val actionView = toggleservice?.actionView as Switch
        actionView.setOnCheckedChangeListener { buttonView, isChecked -> changeEdit(isChecked) }
        return super.onCreateOptionsMenu(menu)
    }

    fun changeEdit(buttonIsChecked: Boolean) {

        for (i in textFields!!.indices) {

            if (buttonIsChecked) {

                textFields!!.get(i).isEnabled = true
                createButton?.setVisibility(View.VISIBLE)

            } else {

                textFields!!.get(i).isEnabled = false
                createButton?.setVisibility(View.GONE)
            }
        }
    }

    fun checkInput(): Boolean {

        if (
                taskName?.getText().toString().isEmpty() ||
                taskTime?.getText().toString().isEmpty() ||
                taskProgress?.getText().toString().isEmpty()
                ) {

            Toast.makeText(context, R.string.input_error_msg, Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }
    }

    fun checkProgress(): Boolean {

        if (Integer.valueOf(taskProgress?.getText().toString()) > 100) {
            Toast.makeText(context, R.string.progress_error_msg, Toast.LENGTH_SHORT).show()
            taskProgress?.getBackground()?.
                    mutate()?.
                    setColorFilter(resources.getColor(R.color.red, null), PorterDuff.Mode.SRC_ATOP)
            return false

        } else {

            return true
        }
    }

    fun onClick(view : View) {

        if (checkInput() && checkProgress()) {

            val thread = Thread(Runnable {
                presenter!!.updateTask(
                        selectedTask!!,
                        taskName!!,
                        taskTime!!,
                        taskStartDate!!,
                        taskDueDate!!,
                        taskDescription!!,
                        taskState!!,
                        taskProgress!!)
            })
            thread.start()
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}