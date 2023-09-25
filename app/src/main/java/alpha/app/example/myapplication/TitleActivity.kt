package alpha.app.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import alpha.app.example.myapplication.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTitleBinding

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 「いちもじ」ボタン
        binding.buttonOneAlphabet.setOnClickListener {
            startActivity(Intent(this@TitleActivity, AlphabetQuizActivity::class.java))
        }

        // 「たんご」ボタン
        binding.buttonWord.setOnClickListener {
            startActivity(Intent(this@TitleActivity, WordQuizActivity::class.java))
        }

        // 「あたらしくはじめる」ボタン
        binding.buttonNewGame.setOnClickListener {
            startActivity(Intent(this@TitleActivity, InputNameActivity::class.java))
        }
    }
}

