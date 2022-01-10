package com.example.downloadbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private val fileSize: Float = 567891F
    private var downloadedSize: Float = 0F
    private lateinit var timer: Timer
    private lateinit var progressPercent: ProgressBar
    private lateinit var tvPercent: TextView
    private lateinit var btnDownload: Button
    private lateinit var btnPause: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressPercent = findViewById(R.id.progressPercent)
        tvPercent = findViewById(R.id.tvPercent)
        btnDownload = findViewById(R.id.btnDownload)
        btnPause = findViewById(R.id.btnPause)
        btnClear = findViewById(R.id.btnClear)

        btnPause.setOnClickListener {
            if (timer != null)
                timer.cancel()
        }

        btnClear.setOnClickListener {
            if (timer != null) {
                downloadedSize = 0F
                timer.cancel()
                progressPercent.progress = 0
                tvPercent.text = "0%"
            }
        }

        btnDownload.setOnClickListener {
            timer = Timer()
            timer.schedule(timerTask {
                if (downloadedSize >= fileSize) {
                    timer.cancel()
                }
                downloadedSize += 10000
                val percent: Int = ((downloadedSize / fileSize) * 100).toInt()
                runOnUiThread {
                    progressPercent.progress = percent.toInt()
                    tvPercent.text = if (percent < 100) "$percent%" else "Download completed"
                }
            }, 100, 100)
        }


    }
}