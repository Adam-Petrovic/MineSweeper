package com.example.minesweeper.domain

import android.util.Log

class GameBoard(
    val width : Int,
    val height : Int,
    val numOfMines : Int) {

    private val board : List<List<BoardCell>>

    init {
        val mines = Mines(width = width, height = height, numOfMines = numOfMines)
        board = createBoardWithMines(width = width, height = height, mines = mines)
        setMineCounts()
    }



    private fun setMineCountsForCellNeighbours(row: Int, column: Int) {
        val cell = board[row][column]
        if (!cell.hasBomb) {
            return
        }
        incrementBombCountForCellAt(row = row - 1, column = column - 1)
        incrementBombCountForCellAt(row = row - 1, column = column    )
        incrementBombCountForCellAt(row = row - 1, column = column + 1)
        incrementBombCountForCellAt(row = row    , column = column - 1)
        incrementBombCountForCellAt(row = row    , column = column + 1)
        incrementBombCountForCellAt(row = row + 1, column = column - 1)
        incrementBombCountForCellAt(row = row + 1, column = column    )
        incrementBombCountForCellAt(row = row + 1, column = column + 1)
    }

    private fun incrementBombCountForCellAt(row: Int, column: Int) {
        if (row < 0 || row > board.lastIndex || column < 0 || column > board[0].lastIndex) {
            return
        }
        ++board[row][column].minesInArea
    }

    private fun setMineCounts() {
        for (row in 0 until board.size) {
            for (column in 0 until board[0].size) {
                setMineCountsForCellNeighbours(row = row, column = column)
            }
        }
    }

    private fun createBoardWithMines(width: Int, height: Int, mines: Mines): List<List<BoardCell>> {
        val board = mutableListOf<List<BoardCell>>()
        for (rowIndex in 0 until height) {
            val row = mutableListOf<BoardCell>()
            for (colIndex in 0 until width) {
                val hasBomb = mines.hasMineAt(row = rowIndex, column = colIndex)
                row.add(BoardCell(hasBomb = hasBomb))
            }
            board.add(row)
        }
        return board
    }

    fun cellAt(position: Int): BoardCell {
        val row = position / board[0].size
        val column = position %  board[0].size

        return board[row][column]
    }
    fun revealCellAt(position: Int) {
        val row = position / board[0].size
        val column = position %  board[0].size

        val cell = board[row][column]

        if (cell.isFlagged) {
            cell.isFlagged = false
        } else {

            revealSurroundingZeros(row = row, column = column)

            if(cell.hasBomb) {
                println("hello")
            }
        }
    }

    private fun revealSurroundingZeros(row: Int, column: Int)  {

        if (row < 0 || row > board.lastIndex || column < 0 || column > board[0].lastIndex) {
            return
        }

        val cell = board[row][column]

        if (cell.isRevealed){
            return
        }

        cell.isRevealed = true

        if (cell.minesInArea != 0 || cell.hasBomb) {
            return
        }
        revealSurroundingZeros(row = row - 1, column = column - 1)
        revealSurroundingZeros(row = row - 1, column = column)
        revealSurroundingZeros(row = row - 1, column = column + 1)
        revealSurroundingZeros(row = row,     column = column - 1)
        revealSurroundingZeros(row = row,     column = column + 1)
        revealSurroundingZeros(row = row + 1, column = column - 1)
        revealSurroundingZeros(row = row + 1, column = column)
        revealSurroundingZeros(row = row + 1, column = column + 1)

    }






    fun placeFlagAt(position: Int) {
        val row = position / board[0].size
        val column = position %  board[0].size
        val cell = board[row][column]
        if (cell.isRevealed) {
            return
        }
        cell.isFlagged = !cell.isFlagged
    }

    fun cellCount(): Int = board.size * board[0].size
}

    data class MinePosition(val row: Int, val column: Int)
    class Mines(width: Int, height: Int, numOfMines: Int) {
        private val mines: List<MinePosition>

        init {
            val tempMines = mutableListOf<MinePosition>()
            for(i in 1..numOfMines) {
                val mineX = (0 until width).random()
                val mineY = (0 until height).random()
                tempMines.add(MinePosition(row = mineY, column = mineX))
            }
            mines = tempMines.sortedWith(compareBy(MinePosition::row, MinePosition::column))
            dumpMines()
        }

        fun hasMineAt(row: Int, column: Int): Boolean
                = mines.find { it.row == row && it.column == column } != null

        private fun dumpMines() {
            Log.v("GameBoard", "dumpMines(): -----------------------------")
            mines.forEachIndexed { index, minePosition ->
                Log.v("GameBoard", "dumpMines(): [$index]: row[${minePosition.row}, column[${minePosition.column}]")
            }
            Log.v("GameBoard", "dumpMines(): -----------------------------")

        }
}