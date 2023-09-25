import android.app.Activity
import java.io.*

class TextWriter(private val activity: Activity) {

    private val fileName = "item_list.txt"
    private val file = File(activity.filesDir, fileName)

    fun saveText(itemList: List<String>) {
        try {
            FileWriter(file).use { writer ->
                for (item in itemList) {
                    writer.write("$item\n") // 改行を追加して一行ずつ書き込む
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


//
//    fun saveText(text: String, itemList: List<String>) {
//        try {
//            FileWriter(file).use { writer ->
//                writer.write(text)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    fun clearFile() {
        try {
            FileWriter(file).use {
                // ファイルを空にするために何も書き込まない
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
