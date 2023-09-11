package my.app.example.myapplication

import android.content.Context
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

class TextWriter(private val context: Context) {
    fun writeItemsToFile(fileName: String, items: List<String>) {
        var writer: BufferedWriter? = null

        try {
            val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            writer = BufferedWriter(OutputStreamWriter(outputStream))

            for (item in items) {
//                FileWriter(fileName).use{ stream -> stream.write(item)
//                    writer.newLine() }
                writer.write(item)
                writer.newLine() // 新しい行に移動
            }
        } catch (e: Exception) {
            println(e)
        } finally {
            try {
                writer?.close()
            } catch (e: IOException) {
                println(e)
            }
        }
    }
}
