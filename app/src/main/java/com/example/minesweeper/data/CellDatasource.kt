package com.example.minesweeper.data

import android.graphics.drawable.Drawable
import com.example.minesweeper.R
import com.example.minesweeper.domain.GameBoard

class CellDatasource(val board : GameBoard) {

    fun loadCells(): ArrayList<Int> {
        val totalTiles : ArrayList<Int> = ArrayList(board.cellCount())
        val bottomEdge = board.height - 1
        val outsideEdge = board.width - 1
        for(row in 0 until bottomEdge + 1) {
            for (column in 0 until outsideEdge + 1) {
                if(column % 2 == 1){
                    totalTiles.add(R.drawable.tile1)
                }
                else{
                    totalTiles.add(R.drawable.tile2)
                }
            }
        }
        return totalTiles
    }
}
