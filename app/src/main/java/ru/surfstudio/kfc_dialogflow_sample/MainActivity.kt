package ru.surfstudio.kfc_dialogflow_sample

import ai.api.AIConfiguration
import ai.api.AIListener
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import ai.api.services.GoogleRecognitionServiceImpl
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import ru.surfstudio.kfc_dialogflow_sample.dialog_flow.AIListenerImpl
import ru.surfstudio.kfc_dialogflow_sample.dialog_flow.TTS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aiListenerImpl = AIListenerImpl()
        val aiService: AIService = AIService.getService(this,
                ai.api.android.AIConfiguration(BuildConfig.DIALOGFLOW_API_KEY,
                        AIConfiguration.SupportedLanguages.Russian,
                        ai.api.android.AIConfiguration.RecognitionEngine.System))

        TTS.init(this)
        aiService.setListener(object : AIListener {
            override fun onResult(result: AIResponse?) {
                Log.d("LOG", "1111 onResult")
                Log.d("LOG", "1111 result response ${result?.result?.fulfillment?.speech}")
                Log.d("LOG", "1111 result parameters ${result?.result?.parameters}")
                TTS.speak(result?.result?.fulfillment?.speech)
            }

            override fun onListeningStarted() {
                Log.d("LOG", "1111 onListeningStarted")
            }

            override fun onAudioLevel(level: Float) {
                Log.d("LOG", "1111 onAudioLevel $level")
            }

            override fun onError(error: AIError?) {
                Log.d("LOG", "1111 onError ${error?.message}")
            }

            override fun onListeningCanceled() {
                Log.d("LOG", "1111 onListeningCanceled")
            }

            override fun onListeningFinished() {
                Log.d("LOG", "1111 onListeningFinished")
            }
        })
        (aiService as? GoogleRecognitionServiceImpl)?.setPartialResultsListener { partialResults ->
            Log.d("LOG", "1111 partialResults = $partialResults")
        }

        val startBtn = findViewById<Button>(R.id.start_speech_btn)
        val stopBtn = findViewById<Button>(R.id.stop_speech_btn)

        startBtn.setOnClickListener { aiService.startListening() }
        stopBtn.setOnClickListener { aiService.startListening() }
    }
}
