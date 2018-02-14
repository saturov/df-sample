package ru.surfstudio.kfc_dialogflow_sample.dialog_flow

import ai.api.AIListener
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.util.Log

/**
 * Процессор запросов на Dialogflow.
 */
class AIListenerImpl : AIListener {

    override fun onResult(result: AIResponse?) {
        Log.d("LOG", "1111 onResult")
        Log.d("LOG", "1111 result action ${result?.result?.action}")
        Log.d("LOG", "1111 result parameters ${result?.result?.parameters}")
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
}