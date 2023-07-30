import android.content.SharedPreferences
import com.android.example.myapplication.R

class Player(private val prefs: SharedPreferences) {

    var name: String = "ななしのごんべ"
    var avatar: Int = 0
    var score: Int = 0
    var imgResources: Int = R.drawable.vikinghelmet_blue
    var level: Int = 1

    init {
        getPlayerInformation()
        checkLevelUp()
    }

    private fun increaseScore(points: Int) {
        score += points
        checkLevelUp()
    }

    private fun checkLevelUp() {
        val levelThresholds = listOf(10, 30, 60, 120, 200)

        while (level < levelThresholds.size && score >= levelThresholds[level - 1]) {
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

    fun getPlayerInformation() {
        name = prefs.getString("userName", "ななしのごんべ").toString()
        avatar = prefs.getInt("avatarNum", 0)
        setAvatarImage()
        score = prefs.getInt("totalScore", 0)
    }

    fun savePlayerInformation() {
        val editor: SharedPreferences.Editor = prefs.edit()
        if (name == "") name = "ななしのごんべ"
        editor.putString("userName", name)
        editor.putInt("totalScore", score)
        editor.putInt("avatarNum", avatar)
        editor.apply()
    }

    fun savePlayerScore(score: Int) {
        increaseScore(score)

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt("totalScore", score)
        editor.apply()
    }
}
