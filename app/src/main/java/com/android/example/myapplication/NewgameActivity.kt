package com.android.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.android.example.myapplication.databinding.ActivityNewgameBinding
import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class NewgameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewgameBinding

    // At the top level of your kotlin file:
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // スタートボタン
        binding.buttonStart.setOnClickListener {

            // なまえを取得
            val name: String = binding.textName.text.toString()

            context.dataStore.edit { settings ->
                val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
                settings[EXAMPLE_COUNTER] = currentCounterValue + 1


                //intentに渡す
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("LABEL_NAME", name)
                startActivity(intent)
            }


        }
    }}

