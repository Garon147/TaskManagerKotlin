package orion.garon.taskmanagerk.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import orion.garon.taskmanagerk.R
import orion.garon.taskmanagerk.database.Task
import java.util.ArrayList

/**
 * Created by VKI on 26.04.2018.
 */
class RecyclerAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {

    private val tasks = ArrayList<Task>()

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {

        holder?.setVars(tasks.get(position))
    }

    override fun getItemCount(): Int {

        val count : Int = tasks.size;
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {

        val view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.item, parent, false)
        return RecyclerViewHolder(view)
    }

    fun addAll(items: List<Task>) {

        val position = itemCount
        this.tasks.addAll(items)
        notifyItemRangeChanged(position, this.tasks.size)
    }

    fun refreshData(tasks: ArrayList<Task>) {

        this.tasks.clear()
        addAll(tasks)
        notifyDataSetChanged()
    }
}