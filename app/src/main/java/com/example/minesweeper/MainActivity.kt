package com.example.minesweeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.adapter.CellAdapter
import com.example.minesweeper.adapter.ItemAdapter
import com.example.minesweeper.data.CellDatasource
import com.example.minesweeper.data.Datasource
import com.example.minesweeper.domain.Game

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val game = Game()
        val boardWidth = 9
        val boardHeight = 12
        val numberOfMines = 20

        game.initialize(boardWidth,boardHeight,numberOfMines)
        val board = game.getBoard()

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = CellAdapter(this, board)
        recyclerView.layoutManager = GridLayoutManager(this, boardWidth)
        recyclerView.isNestedScrollingEnabled = false



    }



}