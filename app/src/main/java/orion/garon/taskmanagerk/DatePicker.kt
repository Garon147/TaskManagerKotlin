package orion.garon.taskmanagerk

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import java.util.*

/**
 * Created by VKI on 24.04.2018.
 */
class DatePicker {

    companion object {

        fun setDatePicker(context: Context?,
                          taskStartDate: EditText?,
                          taskDueDate: EditText?,
                          currentDate: Calendar?) {

            taskStartDate?.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                        context,
                        DatePickerDialog.OnDateSetListener {
                            view,
                            year,
                            month,
                            dayOfMonth -> taskStartDate.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year) },
                        currentDate!!.get(Calendar.YEAR),
                        currentDate!!.get(Calendar.MONTH),
                        currentDate!!.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }

            taskDueDate?.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                        context,
                        DatePickerDialog.OnDateSetListener {
                            view,
                            year,
                            month,
                            dayOfMonth -> taskDueDate?.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year) },
                        currentDate!!.get(Calendar.YEAR),
                        currentDate!!.get(Calendar.MONTH),
                        currentDate!!.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }

            taskStartDate?.isLongClickable = false
            taskDueDate?.isLongClickable = false
        }

        fun createDialog(context: Context?, taskState: EditText?): AlertDialog {

            val onClickListener = DialogInterface.OnClickListener {
                dialog,
                which ->
                val lv = (dialog as AlertDialog).listView
                if (which == AlertDialog.BUTTON_POSITIVE) {
                    taskState?.setText(Status.getAllStates().get(lv.checkedItemPosition))
                }
            }

            val adb = AlertDialog.Builder(context!!)
            adb.setTitle("States")
            val adapter = ArrayAdapter(context, R.layout.select_dialog_singlechoice, Status.getAllStates())
            adb.setSingleChoiceItems(adapter, -1, onClickListener)
            adb.setPositiveButton("OK", onClickListener)
            adb.setNegativeButton("Cancel", onClickListener)

            return adb.create()
        }

        fun setCallback(taskTime: EditText?) {

            taskTime?.isLongClickable = false
        }
    }
}