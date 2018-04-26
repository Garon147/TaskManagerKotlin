package orion.garon.taskmanagerk.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import orion.garon.taskmanagerk.R
import orion.garon.taskmanagerk.database.Task

/**
 * Created by VKI on 26.04.2018.
 */
class RecyclerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    var title: TextView? = null
    var state: TextView? = null
    var dates: TextView? = null
    var progressBar: ProgressBar? = null

    init {

        title = itemView.findViewById(R.id.title) as TextView
        state = itemView.findViewById(R.id.state) as TextView
        dates = itemView.findViewById(R.id.dates) as TextView
        progressBar = itemView.findViewById(R.id.progress) as ProgressBar
    }

    fun setVars(task: Task) {

        title?.setText(task.name)
        state?.setText(task.state)
        dates?.setText("Due " + task.dueDate)
        progressBar?.setProgress(task.completion)
    }
}