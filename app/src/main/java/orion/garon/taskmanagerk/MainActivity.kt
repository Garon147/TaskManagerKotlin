package orion.garon.taskmanagerk

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import orion.garon.taskmanagerk.database.Constants
import orion.garon.taskmanagerk.database.Task
import orion.garon.taskmanagerk.recycler.RecyclerAdapter
import orion.garon.taskmanagerk.recycler.RecyclerItemClickListener

/**
 * Created by VKI on 26.04.2018.
 */
class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    var fab: FloatingActionButton? = null

    var toolbar: Toolbar? = null

    private var layoutManager: LinearLayoutManager? = null
    private var recyclerAdapter: RecyclerAdapter? = null
    private var presenter: Presenter? = null

    private var taskFragment: TaskFragment? = null
    private var fragmentManager: FragmentManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        presenter = Presenter(this)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        taskFragment = TaskFragment()
        fragmentManager = supportFragmentManager

        initViews()

    }

    fun initViews() {

        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView?.setLayoutManager(layoutManager)

        recyclerAdapter = RecyclerAdapter()
        recyclerView?.setAdapter(recyclerAdapter)
        recyclerAdapter!!.addAll(presenter!!.tasks)
        recyclerAdapter!!.refreshData(presenter!!.tasks)

        recyclerView?.addOnItemTouchListener(RecyclerItemClickListener(this, recyclerView!!, object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {

                val intent = Intent(applicationContext, ShowTaskActivity::class.java)
                makeIntent(presenter!!.tasks.get(position), intent)
                startActivity(intent)
            }

            override fun onLongItemClick(view: View, position: Int) {

            }
        }))

        fab = findViewById(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener(View.OnClickListener { v: View? -> onClick(v!!) })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            this.finish()
        } else {
            supportFragmentManager.popBackStack()
            fab?.setVisibility(View.VISIBLE)
        }

        supportActionBar!!.setTitle(R.string.app_name)
    }

    fun makeIntent(task: Task, intent: Intent) {

        intent.putExtra(Constants.ID, task.id)
        intent.putExtra(Constants.NAME, task.name)
        intent.putExtra(Constants.COMPLETION, task.completion)
        intent.putExtra(Constants.DESCRIPTION, task.description)
        intent.putExtra(Constants.DUE_DATE, task.dueDate)
        intent.putExtra(Constants.START_DATE, task.startDate)
        intent.putExtra(Constants.ESTIMATED_TIME, task.estimatedTime)
        intent.putExtra(Constants.STATE, task.state)
    }

    fun onClick(view : View) {
        fragmentManager?.
                beginTransaction()?.
                add(R.id.frame_layout, taskFragment)?.
                addToBackStack(null)?.
                commit()
        toolbar?.setTitle(R.string.create_task_toolbar)
        fab?.setVisibility(View.GONE)
    }
}