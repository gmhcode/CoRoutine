package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "RESULT #1"
    private val RESULT_2 = "RESULT #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private suspend fun fakeApiRequest() {
        val result1 = getResult1FromApi()
        println("debug:  $result1")
        setTextOnMainThread(result1)

        val result2 = getResult2FromApi()
        setTextOnMainThread(result2)
    }
    private suspend fun setTextOnMainThread(input: String) {
        //main thread
        withContext(Main) {
            setNewText(input)
        }
    }
    private fun setNewText(input: String) {
        val newText = textView.text.toString() + "\n$input"
        textView.text = newText
    }


    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
//        this only delays a single coroutine
        delay(1000)
        return RESULT_1
    }
    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
//        this only delays a single coroutine
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}