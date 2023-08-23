import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.android.example.myapplication.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class Player(preferences: SharedPreferences, context: Context) {
    private var prefs = preferences
    var name: String = "ななしのごんべ"
    var avatar: Int = 0
    var totalScore: Int = 0
    var imgResources: Int = 0
    var level: Int = 1
    val levelThresholds = listOf(10, 20, 30, 40, 50)
    var itemList = listOf<String>()


    init {
        itemSet(context)
        getPlayerInformation()
        checkLevelUp()
    }

    // アイテムゲットシステムここから
    private fun itemSet(context: Context) {
        val textReader = TextReader(context)
        itemList = textReader.readItemFromFile("item_list.txt").toMutableList()
        }

    private fun increaseScore(points: Int) {
        totalScore += points
    }

    fun checkLevelUp(): Boolean {
        var levelUpOccurred = false
        while (level < levelThresholds.size && totalScore >= levelThresholds[level - 1]) {
            level++
            levelUpOccurred = true
        }
        return levelUpOccurred
    }

    private fun setAvatarImage() {
        when (avatar) {
            0 -> imgResources = R.drawable.character_hime_01_blue_gold
            1 -> imgResources = R.drawable.character_yusha_01_red
            2 -> imgResources = R.drawable.character_tenshi_brown
        }
    }


    @SuppressLint("CommitPrefEdits")
    private fun getPlayerInformation() {
        name = prefs.getString("userName", "ななしのごんべ").toString()
        avatar = prefs.getInt("avatarNum", 0)
        setAvatarImage()
        totalScore = prefs.getInt("totalScore", 0)
    }

    fun savePlayerScore(points: Int) {
        increaseScore(points)
        val editor: SharedPreferences.Editor = prefs.edit()
        if (name == "") name = "ななしのごんべ"
        editor.putString("userName", name)
        editor.putInt("avatarNum", avatar)
        editor.putInt("totalScore", totalScore)
        editor.apply()
    }
}









