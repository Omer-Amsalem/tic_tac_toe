package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var board: Array<Array<Button>>
    private var currentPlayer = 1
    private var gameBoard = Array(3) { IntArray(3) { 0 } }
    private lateinit var winnerText: TextView
    private lateinit var startAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
            arrayOf(
                findViewById(R.id.up_left_button),
                findViewById(R.id.up_middle_button),
                findViewById(R.id.up_right_button)
            ),
            arrayOf(
                findViewById(R.id.center_left_button),
                findViewById(R.id.center_middle_button),
                findViewById(R.id.center_right_button)
            ),
            arrayOf(
                findViewById(R.id.down_left_button),
                findViewById(R.id.down_middle_button),
                findViewById(R.id.down_right_button)
            )
        )

        winnerText = findViewById(R.id.wining_text)
        startAgainButton = findViewById(R.id.start_again_button)

        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].setOnClickListener {
                    onCellClicked(i, j)
                }
            }
        }

        startAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (gameBoard[row][col] == 0) {
            gameBoard[row][col] = currentPlayer
            board[row][col].text = if (currentPlayer == 1) "X" else "O"
            if (checkWinner()) {
                winnerText.text = "Player ${if (currentPlayer == 1) "X" else "O"} Wins!"
                disableBoard()
            } else if (isBoardFull()) {
                winnerText.text = "It's a Draw!"
            } else {
                currentPlayer = if (currentPlayer == 1) 2 else 1
            }
        }
    }

    private fun checkWinner(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (gameBoard[i][0] != 0 && gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2]) return true
            if (gameBoard[0][i] != 0 && gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i]) return true
        }

        // Check diagonals
        if (gameBoard[0][0] != 0 && gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) return true
        if (gameBoard[0][2] != 0 && gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in gameBoard) {
            for (cell in row) {
                if (cell == 0) return false
            }
        }
        return true
    }

    private fun disableBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        gameBoard = Array(3) { IntArray(3) { 0 } }
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].text = ""
                board[i][j].isEnabled = true
            }
        }
        currentPlayer = 1
        winnerText.text = ""
    }
}

