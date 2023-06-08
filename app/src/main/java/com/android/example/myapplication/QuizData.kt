import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_table")
data class QuizData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "answer")
    val answer: String,

    @ColumnInfo(name = "choice1")
    val choice1: String,

    @ColumnInfo(name = "choice2")
    val choice2: String,

    @ColumnInfo(name = "choice3")
    val choice3: String
)