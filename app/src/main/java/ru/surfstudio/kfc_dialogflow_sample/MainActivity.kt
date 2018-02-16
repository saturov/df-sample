package ru.surfstudio.kfc_dialogflow_sample

import ai.api.AIConfiguration
import ai.api.AIListener
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import ai.api.services.GoogleRecognitionServiceImpl
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import ru.surfstudio.kfc_dialogflow_sample.dialog_flow.TTS

val PERMISSIONS_REQUEST_MIC: Int = 123

class MainActivity : AppCompatActivity() {

    val aiService: AIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aiService: AIService = AIService.getService(this,
                ai.api.android.AIConfiguration(BuildConfig.DIALOGFLOW_API_KEY,
                        AIConfiguration.SupportedLanguages.Russian,
                        ai.api.android.AIConfiguration.RecognitionEngine.System))


        aiService.setListener(object : AIListener {
            override fun onResult(result: AIResponse?) {
                Log.d("LOG", "1111 onResult")
                Log.d("LOG", "1111 result response ${result?.result?.fulfillment?.speech}")
                Log.d("LOG", "1111 result parameters ${result?.result?.parameters}")
                TTS.init(this@MainActivity)
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

        startBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.RECORD_AUDIO)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            PERMISSIONS_REQUEST_MIC)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                aiService?.startListening()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_MIC -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    aiService?.startListening()
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
