package com.example.minesweeper

import android.view.View
import com.example.minesweeper.adapter.ItemAdapter


class Board(private val width : Int, private val height : Int, private val numOfMines : Int) {

    //The Game Board
    var board = Array(height) { IntArray(width) }
    var flagLocations = ArrayList<Pair<Int, Int>>()


    // Generates Entire Board
    fun generateBoard(): Array<IntArray> {
        for (i in board) {
            for (x in 0 until board[0].size) {
                i[x] = 0
            }
        }

        placeMines()
        updateSquares()
        return board
    }

    //Checks if pressed tile has a mine
    fun isMinePressed(position: Int): Boolean {

        val row = position / getBoardWidth()
        val column = position %  getBoardWidth()

        if (board[row][column] == 7) {
            println("Mine Detected at ${position}, or ${row}, ${column}")

            return true
        }

        println("chillin at ${position}, or ${row}, ${column}")
        return false
    }


    //Checks whether there is a flag there
    fun hasFlag(position: Int) : Boolean {

        val row = position / getBoardWidth()
        val column = position % getBoardWidth()
        val flagCoordinate = Pair(row, column)

        if (flagLocations.contains(flagCoordinate))
            return true
        return false
    }

    //Places or removes flags
    fun placeFlag(holder: ItemAdapter.ItemViewHolder, position: Int): Boolean {
        val row = position / getBoardWidth()
        val column = position %  getBoardWidth()
        val flagCoordinate = Pair(row, column)

        if(hasFlag(position)){
            holder.flagTile.visibility = View.GONE
            flagLocations.remove(flagCoordinate)
        }

        else{
            holder.flagTile.visibility = View.VISIBLE
            flagLocations.add(flagCoordinate)
        }


        return true
    }

    //Retrives Game Board Height
    fun getBoardHeight() = board.size

    //Retrieves Game Board Width
    fun getBoardWidth() = board[0].size

    //Places Mines
    private fun placeMines(){
        board[board.size-1][board[0].size-1] = 7
        for(i in 1..numOfMines) {
            val mineY = (0 until board.size).random()
            val mineX = (0 until board[0].size).random()
            board[mineY][mineX] = 7
        }
    }

    //Updates the squares surrounding mines
    private fun updateSquares(){
        val topEdge = 0
        val bottomEdge = board.size - 1

        val insideEdge = 0
        val outsideEdge = board[0].size - 1


        for(row in 0 until bottomEdge + 1){
            for (column in 0 until outsideEdge + 1){
                //ensures it is not a bomb
                if(board[row][column] == 7){

                    //checking left side
                    if(column != insideEdge){
                        if(board[row][column-1] != 7){
                            board[row][column-1] += 1
                        }
                        if(row != bottomEdge){
                            if(board[row+1][column-1] != 7) {
                                board[row + 1][column - 1] += 1
                            }
                        }

                        if(row != topEdge){
                            if(board[row-1][column-1] != 7) {
                                board[row - 1][column - 1] += 1
                            }
                        }
                    }

                    //checking right side
                    if(column != outsideEdge){
                        if(board[row][column+1] != 7) {
                            board[row][column + 1] += 1
                        }
                        if(row != bottomEdge){
                            if(board[row+1][column + 1] != 7) {
                                board[row+1][column + 1] += 1
                            }
                        }

                        if(row != topEdge){
                            if(board[row-1][column+1] != 7) {
                                board[row - 1][column + 1] += 1
                            }
                        }
                    }

                    //checking above
                    if(row != topEdge){
                        if(board[row-1][column] != 7) {
                            board[row - 1][column] += 1
                        }
                    }

                    //checking below
                    if(row != bottomEdge){
                        if(board[row+1][column] != 7) {
                            board[row + 1][column] += 1
                        }
                    }
                }
            }
        }
    }
}