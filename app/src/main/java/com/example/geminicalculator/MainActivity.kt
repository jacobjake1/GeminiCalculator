package com.example.geminicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var inputEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var model: GenerativeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)

        model = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        calculateButton.setOnClickListener { performCalculation() }
    }

    private fun performCalculation() {
        val input = inputEditText.text.toString()
        val prompt = "Calculate the result of: $input"

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = model.generateContent(content {
                    text(prompt)
                })
                resultTextView.text = "Result: ${response.text}"
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            }
        }
    }
}