import android.app.Activity
import android.content.Context
import java.io.*

class TextReader(private val context: Context) {
    private var itemList = mutableListOf<String>()
    private val file = File(context.filesDir, "item_list.txt")

    fun readItemFromFile(fileName: String): List<String> {
        var text: String? = null

        try {
            val br = BufferedReader(FileReader(file))
            while (br.readLine().also { text = it } != null) {
                val trimmedStr = text?.trim() // 行から前後の空白を除去
                if (trimmedStr != null) {
                    if (trimmedStr.isNotBlank()) { // 空白でない場合のみ処理を行う
                        val lineItems = trimmedStr.split("\r\n") // カンマで区切ってリストを取得
                        itemList.addAll(lineItems) // リストの要素を itemList に追加
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return itemList
    }
}
