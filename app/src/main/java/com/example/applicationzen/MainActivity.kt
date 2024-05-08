package com.example.applicationzen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity(){
    private val RQ_SPEECH_REC = 102
    private lateinit var txtSpeechInput: TextView
    private lateinit var btnSpeak: Button

    //val tvtext = findViewById<TextView>(R.id.tv_text)
    //val btnbutton = findViewById<Button>(R.id.btn_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txtSpeechInput = findViewById(R.id.tv_text)
        btnSpeak = findViewById(R.id.btn_button)

        btnSpeak.setOnClickListener {
            startVoiceInput()
        }

        /*btn_button.setOnClickListener {
            askSpeechInput()
        }*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startVoiceInput(){
        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")

        try {
            startActivityForResult(i, RQ_SPEECH_REC)
        } catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            RQ_SPEECH_REC -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    txtSpeechInput.text = result?.get(0).toString()
                }
            }
        }
        /*if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val ArrayList = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            //tv_text.text = ArrayList?.get(0).toString()
        }*/
    }

    private fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")


            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }
}