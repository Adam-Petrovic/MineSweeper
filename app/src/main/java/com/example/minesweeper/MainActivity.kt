package com.example.minesweeper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.adapter.CellAdapter
import com.example.minesweeper.domain.Game

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
    }
}