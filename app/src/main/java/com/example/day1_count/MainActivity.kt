package com.example.day1_count

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var playerOneCount: Int = 0
    var playerTwoCount: Int = 0
    var second = 60

    var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            startButton.isVisible = true
            second = 60
            timerTextView.text = second.toString()
            finishGame()
        }

        override fun onTick(p0: Long) {
            second = second - 1
            if (second <= 10) {
                timerTextView.setTextColor(Color.RED)
            }
            timerTextView.text = second.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1Button.isEnabled = false
        player2Button.isEnabled = false
        player1AttackButton.isEnabled = false
        player2AttackButton.isEnabled = false
        startButton.isEnabled = true
        startButton.visibility = View.VISIBLE
        timerLayout.visibility = View.INVISIBLE

        startButton.setOnClickListener {
            player1Button.isEnabled = true
            player2Button.isEnabled = true
            player1AttackButton.isEnabled = true
            player2AttackButton.isEnabled = true
            startButton.isEnabled = false
            startButton.visibility = View.INVISIBLE
            timerLayout.visibility = View.VISIBLE
            timer.start()
        }

        player1Button.setOnClickListener {
            playerOneCount += 1
            player1Text.text = playerOneCount.toString()
        }

        player2Button.setOnClickListener {
            playerTwoCount += 1
            player2Text.text = playerTwoCount.toString()
        }

        player1AttackButton.setOnClickListener {
            playerTwoCount -= 1
            player2Text.text = playerTwoCount.toString()
        }

        player2AttackButton.setOnClickListener {
            playerOneCount -= 1
            player1Text.text = playerOneCount.toString()
        }
    }

    fun finishGame() {
        player1Button.isEnabled = false
        player2Button.isEnabled = false
        player1AttackButton.isEnabled = false
        player2AttackButton.isEnabled = false
        timerLayout.visibility = View.INVISIBLE

        var title: String = ""

        if (playerOneCount < playerTwoCount) {
            title = "プレイヤー赤の勝利！"
        } else if (playerOneCount > playerTwoCount) {
            title = "プレイヤー青の勝利！"
        } else {
            title = "引き分け"
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("OK") { _, _ ->
                playerOneCount = 0
                playerTwoCount = 0
                player1Text.text = playerOneCount.toString()
                player2Text.text = playerTwoCount.toString()
                startButton.isEnabled = true
                startButton.visibility = View.VISIBLE
            }
            .show()
    }
}