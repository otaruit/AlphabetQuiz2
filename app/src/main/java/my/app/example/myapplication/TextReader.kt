import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class TextReader(private val context: Context) {
    private var itemList = mutableListOf<String>()

//    fun readTextFromAssets(fileName: String): String {
//        val inputStream: InputStream = context.assets.open(fileName)
//        return inputStream.bufferedReader().use(BufferedReader::readText)
//    }

    fun readItemFromFile(fileName: String): List<String> {
        var inputStream: InputStream? = null
        var br: BufferedReader? = null

        try {
            inputStream = context.assets.open(fileName)
            br = BufferedReader(InputStreamReader(inputStream))
            var str: String

            while (br.readLine().also { str = it } != null) {
                val trimmedStr = str.trim() // 行から前後の空白を除去
                if (trimmedStr.isNotBlank()) { // 空白でない場合のみ処理を行う
                    val lineItems = trimmedStr.split("\r\n") // カンマで区切ってリストを取得
                    itemList.addAll(lineItems) // リストの要素を itemList に追加
                }
            }


        } catch (e: Exception) {
            println(e)
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                println(e)
            }
            try {
                br?.close()
            } catch (e: IOException) {
                println(e)
            }
        }

        return itemList
    }
}
