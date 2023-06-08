import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizDataDao {
    @Insert
    suspend fun insert(quiz: QuizData)

    @Delete
    suspend fun delete(quiz: QuizData)

    @Query("DELETE FROM quiz_table")
    suspend fun clear()

    @Query("SELECT * FROM quiz_table WHERE id = :key")
    suspend fun get(key: Long): QuizData?
}