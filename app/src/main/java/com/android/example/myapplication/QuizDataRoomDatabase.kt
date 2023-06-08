import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuizData::class], version = 1, exportSchema = false)
abstract class QuizDataRoomDatabase : RoomDatabase() {
    abstract fun quizDataDao(): QuizDataDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDataRoomDatabase? = null
        fun getDatabase(context: Context): QuizDataRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDataRoomDatabase::class.java,
                    "quiz_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}