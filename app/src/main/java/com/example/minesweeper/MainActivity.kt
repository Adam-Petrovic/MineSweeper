package com.example.minesweeper

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.adapter.CellAdapter
import com.example.minesweeper.domain.Game
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val game = Game()
        val boardWidth = 9
        val boardHeight = 12
        val numberOfMines = 10

        game.initialize(boardWidth,boardHeight,numberOfMines)
        val board = game.getBoard()

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = CellAdapter(board, object : CellAdapter.GameEventsObserver {
            override fun onBombRevealed() {
                onBombRevealedEvent()
            }
        })
        recyclerView.layoutManager = GridLayoutManager(this, boardWidth)
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun onBombRevealedEvent() {
        Log.i("MineActivity", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        Log.i("MineActivity", "KABOOOOOOOM!!!!!!!!!!")
        Log.i("MineActivity", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

        val dialog = BottomSheetDialog(this)

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.game_over_bottom_sheet_dialog, null)

        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val elapsedTime = view.findViewById<TextView>(R.id.elapsedTime)
        val correctFlagCount = view.findViewById<TextView>(R.id.correctFlagCount)
        val minesRemaining = view.findViewById<TextView>(R.id.minesRemaining)

        val buttonNewGame = view.findViewById<Button>(R.id.buttonNewGame)
        val buttonSettings = view.findViewById<Button>(R.id.buttonSettings)

        val gameStats = getGameStats()
        elapsedTime.text = gameStats.elapsedTimeInSeconds.toString()
        correctFlagCount.text = gameStats.correctFlagsCount.toString()
        minesRemaining.text = gameStats.remainingMinesCount.toString()

        buttonNewGame.setOnClickListener {
            dialog.dismiss()
        }

        buttonSettings.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    data class GameStats(
        val totalMinesCount: Int,
        val remainingMinesCount: Int,
        val totalFlagsCount: Int,
        val correctFlagsCount: Int,
        val elapsedTimeInSeconds: Int,
    )

    private fun getGameStats(): GameStats {
        return GameStats(
            totalMinesCount = 20,
            remainingMinesCount = 5,
            totalFlagsCount = 7,
            correctFlagsCount = 4,
            elapsedTimeInSeconds = 1234,
        )
    }
}