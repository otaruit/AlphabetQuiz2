import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.widget.ImageView
import com.android.example.myapplication.R

class Player(private val prefs: SharedPreferences) {

    var name: String = "ななしのごんべ"
    var avatar: String = "character_ningyo_01_blue_purple"
   var score: Int = 0
    private val characterNum = 0

    var avatarImg: ImageView? = null
//    var avatarImg: ImageView = ImageView(context)

    var level: Int = 1

    init {
        getPlayerInformation()
        checkLevelUp()
    }

    fun increaseScore(points: Int) {
        score += points
        checkLevelUp()
    }

    private fun checkLevelUp() {
        val levelThresholds = listOf(10, 30, 60, 120, 200)

        while (level < levelThresholds.size && score >= levelThresholds[level - 1]) {
            level++
        }
    }

    private fun selectAvatarImage() {
        when (characterNum) {
            0 -> avatar = "character_ningyo_01_blue_purple"
            // 必要に応じて、他のcharacterNum値とそれに対応するアバターのケースを追加してください。
            else -> avatar = "character_ningyo_01_blue_purple" // デフォルトのアバター
        }
//        val resourceId = context.resources.getIdentifier(avatar, "drawable", context.packageName)
//        avatarImg?.setImageResource(resourceId)
    }

    fun getPlayerInformation() {
        name = prefs.getString("userName", "ななしのごんべ").toString()

        selectAvatarImage()

        score = prefs.getInt("totalScore", 0)
    }

    fun savePlayerInformation() {
        val editor: SharedPreferences.Editor = prefs.edit()
        if(name=="") name = "ななしのごんべ"
        editor.putString("userName", name)
        editor.putInt("totalScore", score)
        editor.putInt("characterNum", characterNum)
        editor.apply()
    }

    fun savePlayerScore(score:Int) {
        increaseScore(score)

        val editor: SharedPreferences.Editor = prefs.edit()
        if(name=="") name = "ななしのごんべ"
        editor.putString("userName", name)
        editor.putInt("totalScore", score)
        editor.putInt("characterNum", characterNum)
        editor.apply()
    }
}
