package orion.garon.taskmanagerk

import java.util.ArrayList

/**
 * Created by VKI on 24.04.2018.
 */
enum class Status(var stringVal: String) {

    NEW("New"),
    INPROGRESS("In Progress"),
    DONE("Done");

//    var stringValue: String
//
//    private fun Status(stringVal: String): Status {
//        stringValue = stringVal
//    }

    companion object {

        fun getAllStates(): List<String> {

            val items = ArrayList<String>()
            items.add(NEW.toString())
            items.add(INPROGRESS.toString())
            items.add(DONE.toString())

            return items
        }
    }

//
//    override fun toString(): String {
//        return stringValue
//    }
}