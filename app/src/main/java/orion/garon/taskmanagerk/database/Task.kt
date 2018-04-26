package orion.garon.taskmanagerk.database

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by VKI on 27.04.2018.
 */

const val ID = "id"
const val NAME = "name"
const val COMPLETION = "completion"
const val STATE = "state"
const val ESTIMATED_TIME = "estimated_time"
const val START_DATE = "start_date"
const val DUE_DATE = "due_date"
const val DESCRIPTION = "description"

@DatabaseTable(tableName = "tasks")
//class Task (name : String ,completion : Int, state : String, estimatedTime : Float, startDate : String,
//            dueDate : String, description : String){
class Task {

    @DatabaseField(generatedId = true)
    var id: Int = 0

    @DatabaseField(dataType = DataType.STRING, columnName = NAME)
    var name: String = ""

    @DatabaseField(dataType = DataType.INTEGER, columnName = COMPLETION)
    var completion: Int = 0

    @DatabaseField(dataType = DataType.STRING, columnName = STATE)
    var state: String = ""

    @DatabaseField(dataType = DataType.FLOAT, columnName = ESTIMATED_TIME)
    var estimatedTime: Float = 0f

    @DatabaseField(dataType = DataType.STRING, columnName = START_DATE)
    var startDate: String = ""

    @DatabaseField(dataType = DataType.STRING, columnName = DUE_DATE)
    var dueDate: String = ""

    @DatabaseField(dataType = DataType.STRING, columnName = DESCRIPTION, canBeNull = true)
    var description: String = ""

    constructor(name : String ,completion : Int, state : String, estimatedTime : Float, startDate : String,
                dueDate : String, description : String) {
        this.name = name;
        this.completion = completion
        this.state = state
        this.estimatedTime = estimatedTime
        this.startDate = startDate
        this.dueDate = dueDate
        this.description = description
    }

    constructor() {}
}