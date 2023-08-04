import android.content.SharedPreferences
import com.android.example.myapplication.R

class Player(preferences: SharedPreferences) {
    private var prefs = preferences
    var name: String = "ななしのごんべ"
    var avatar: Int = 0
    var totalScore: Int = 0
    var imgResources: Int = R.drawable.vikinghelmet_blue
    var level: Int = 1
    val levelThresholds = listOf(10, 30, 60, 120, 200)

    init {
        getPlayerInformation()
        checkLevelUp()
    }

    private fun increaseScore(points: Int) {
        totalScore += points
        checkLevelUp()
    }

    private fun checkLevelUp() {
        while (level < levelThresholds.size && totalScore >= levelThresholds[level - 1]) {
            level++
        }
    }

    private fun setAvatarImage() {
        when (avatar) {
            0 -> imgResources = R.drawable.character_ningyo_01_blue_purple
            1 -> imgResources = R.drawable.woman
            2 -> imgResources = R.drawable.vikinghelmet_blue
        }
//        val resourceId = context.resources.getIdentifier(avatar, "drawable", context.packageName)
//        avatarImg?.setImageResource(resourceId)
    }

    private fun getPlayerInformation() {
        name = prefs.getString("userName", "ななしのごんべ").toString()
        avatar = prefs.getInt("avatarNum", 0)
        setAvatarImage()
        totalScore = prefs.getInt("totalScore", 0)
    }

    fun savePlayerInformation() {
        val editor: SharedPreferences.Editor = prefs.edit()
        if (name == "") name = "ななしのごんべ"
        editor.putString("userName", name)
        editor.putInt("totalScore", totalScore)
        editor.putInt("avatarNum", avatar)
        editor.apply()
    }

    fun savePlayerScore(points: Int) {
        increaseScore(points)

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt("totalScore", totalScore)
        editor.apply()
    }
}
