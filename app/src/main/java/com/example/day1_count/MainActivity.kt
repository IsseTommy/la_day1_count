package com.example.day1_count

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var playerOneCount: Int = 0
    var playerTwoCount: Int = 0
    var second = 60

    var timer: CountDownTimer = object : CountDownTimer((second * 1000).toLong(), 1000) {
        override fun onFinish() {
            startButton.isVisible = true
            second = 60
            timerTextView.text = second.toString()
            finishGame()
        }

        override fun onTick(p0: Long) {
            second -= 1
            if (second <= 10) {
                timerTextView.setTextColor(Color.RED)
            } else {
                timerTextView.setTextColor(Color.BLACK)
            }
            timerTextView.text = second.toString()
        }
    }

    val attackTimerOne: CountDownTimer = object : CountDownTimer(5000, 1000) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            player1AttackButton.isEnabled = true
            player1AttackButton.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.attack_active)
        }
    }

    val attackedTimerOne: CountDownTimer = object : CountDownTimer(2000, 1000) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            player1Button.isEnabled = true
            player1Button.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.add_one_active)
        }
    }

    val attackTimerTwo: CountDownTimer = object : CountDownTimer(5000, 1000) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            player2AttackButton.isEnabled = true
            player2AttackButton.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.attack_active)
        }
    }

    val attackedTimerTwo: CountDownTimer = object : CountDownTimer(2000, 1000) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            player2Button.isEnabled = true
            player2Button.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.add_two_active)
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
            val strList = arrayOf("15秒","30秒","60秒")

            AlertDialog.Builder(this)
                    .setTitle("時間を選択してください")
                    .setItems(strList) { _, which ->
                        when (which) {
                            0 -> {
                                second = 15
                            }
                            1 -> {
                                second = 30
                            }
                            2 -> {
                                second = 60
                            }
                            else -> {
                                Log.e("ERROR", "INVALID NUMBER")
                            }
                        }
                        timer = object : CountDownTimer((second * 1000).toLong(), 1000) {
                            override fun onFinish() {
                                startButton.isVisible = true
                                second = 60
                                timerTextView.text = second.toString()
                                finishGame()
                            }

                            override fun onTick(p0: Long) {
                                second -= 1
                                if (second <= 10) {
                                    timerTextView.setTextColor(Color.RED)
                                } else {
                                    timerTextView.setTextColor(Color.BLACK)
                                }
                                timerTextView.text = second.toString()
                            }
                        }
                        player1Button.isEnabled = true
                        player2Button.isEnabled = true
                        player1AttackButton.isEnabled = true
                        player2AttackButton.isEnabled = true
                        startButton.isEnabled = false
                        startButton.visibility = View.INVISIBLE
                        timerLayout.visibility = View.VISIBLE
                        timer.start()
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
        }

        player1Button.setOnClickListener {
            playerOneCount += 1
            displayNumbers()
        }

        player2Button.setOnClickListener {
            playerTwoCount += 1
            displayNumbers()
        }

        player1AttackButton.setOnClickListener {
            attackView.visibility = View.VISIBLE
            attackView.rotation = 0F
            attackView.playAnimation()
            attackTimerOne.start()
            attackedTimerTwo.start()
            player2Button.isEnabled = false
            player2Button.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.inactive)
            player1AttackButton.isEnabled = false
            player1AttackButton.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.inactive)
        }

        player2AttackButton.setOnClickListener {
            attackView.visibility = View.VISIBLE
            attackView.rotation = 180F
            attackView.playAnimation()
            attackTimerTwo.start()
            attackedTimerOne.start()
            player1Button.isEnabled = false
            player1Button.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.inactive)
            player2AttackButton.isEnabled = false
            player2AttackButton.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.inactive)
        }
    }

    fun finishGame() {
        player1Button.isEnabled = false
        player2Button.isEnabled = false
        player1AttackButton.isEnabled = false
        player2AttackButton.isEnabled = false
        timerLayout.visibility = View.INVISIBLE

        val title = when {
            playerOneCount < playerTwoCount -> {
                "プレイヤー赤の勝利！"
            }
            playerOneCount > playerTwoCount -> {
                "プレイヤー青の勝利！"
            }
            else -> {
                "引き分け"
            }
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("OK") { _, _ ->
                playerOneCount = 0
                playerTwoCount = 0
                displayNumbers()
                startButton.isEnabled = true
                startButton.visibility = View.VISIBLE
            }
            .show()
    }

    private fun displayNumbers() {
        player1Text.text = playerOneCount.toString()
        player2Text.text = playerTwoCount.toString()

        when {
            playerOneCount > 50 -> {
                player1Text.setTextColor(Color.RED)
            }
            playerOneCount < 0 -> {
                player1Text.setTextColor(Color.BLUE)
            }
            else -> {
                player1Text.setTextColor(Color.BLACK)
            }
        }

        when {
            playerTwoCount > 50 -> {
                player2Text.setTextColor(Color.RED)
            }
            playerTwoCount < 0 -> {
                player2Text.setTextColor(Color.BLUE)
            }
            else -> {
                player2Text.setTextColor(Color.BLACK)
            }
        }
    }
}