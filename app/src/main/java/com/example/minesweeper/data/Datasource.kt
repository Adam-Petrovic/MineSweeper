package com.example.minesweeper.data

import com.example.minesweeper.model.Tile
import com.example.minesweeper.R


class Datasource(val board : Array<IntArray>) {

    fun loadTiles(): ArrayList<Tile> {
        val totalTiles : ArrayList<Tile> = ArrayList(board.size*board.size)
        val bottomEdge = board.size - 1
        val outsideEdge = board[0].size - 1
        var counter = 0
        for(row in 0 until bottomEdge + 1){
            for (column in 0 until outsideEdge + 1){



                if(counter % 2 == 1) {
                    when(board[row][column]) {
                        1 ->       totalTiles.add(Tile(R.drawable.tile1, R.drawable.one, R.drawable.flag))
                        2 ->       totalTiles.add(Tile(R.drawable.tile1, R.drawable.two, R.drawable.flag))
                        3 ->     totalTiles.add(Tile(R.drawable.tile1, R.drawable.three, R.drawable.flag))
                        4 ->      totalTiles.add(Tile(R.drawable.tile1, R.drawable.four, R.drawable.flag))
                        5 ->      totalTiles.add(Tile(R.drawable.tile1, R.drawable.five, R.drawable.flag))
                        6 ->       totalTiles.add(Tile(R.drawable.tile1, R.drawable.six, R.drawable.flag))
                        7 -> totalTiles.add(Tile(R.drawable.tile1, R.drawable.explosion, R.drawable.flag))
                        else ->   totalTiles.add(Tile(R.drawable.tile1, R.drawable.zero, R.drawable.flag))
                    }

                }



                else {

                    when(board[row][column]) {

                        1 ->       totalTiles.add(Tile(R.drawable.tile2, R.drawable.one, R.drawable.flag))
                        2 ->       totalTiles.add(Tile(R.drawable.tile2, R.drawable.two, R.drawable.flag))
                        3 ->     totalTiles.add(Tile(R.drawable.tile2, R.drawable.three, R.drawable.flag))
                        4 ->      totalTiles.add(Tile(R.drawable.tile2, R.drawable.four, R.drawable.flag))
                        5 ->      totalTiles.add(Tile(R.drawable.tile2, R.drawable.five, R.drawable.flag))
                        6 ->       totalTiles.add(Tile(R.drawable.tile2, R.drawable.six, R.drawable.flag))
                        7 -> totalTiles.add(Tile(R.drawable.tile2, R.drawable.explosion, R.drawable.flag))
                        else ->   totalTiles.add(Tile(R.drawable.tile2, R.drawable.zero, R.drawable.flag))
                    }
                }

                counter++

            }
        }
        return totalTiles
   }
}
