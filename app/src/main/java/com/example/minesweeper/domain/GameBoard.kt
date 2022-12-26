package com.example.minesweeper.domain

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

    enum class RevealCellResult {
        Ok, Explosion
    }

    fun cellAt(position: Int): BoardCell {
        val row = position / board[0].size
        val column = position %  board[0].size

        return board[row][column]
    }

    data class CellPosition(
        val row: Int,
        val column: Int,
    )

    private fun Int.toCellPosition(): CellPosition
        = CellPosition(row = this / board[0].size, column = this %  board[0].size)

    private fun CellPosition.toCell(): BoardCell
        = board[row][column]

    private fun Int.toCell(): BoardCell
        = this.toCellPosition().toCell()

    fun revealCellAt(position: Int): RevealCellResult {
        val cellPosition = position.toCellPosition()
        val cell = position.toCell()

        if (cell.isFlagged) {
            cell.isFlagged = false
        } else {
            revealSurroundingZeros(cellPosition)
            if(cell.hasBomb) {
                return RevealCellResult.Explosion
            }
        }
        return RevealCellResult.Ok
    }

    fun placeFlagAt(position: Int) {
        val cell = position.toCell()
        if (cell.isRevealed) {
            return
        }
        cell.isFlagged = !cell.isFlagged
    }

    fun cellCount(): Int = board.size * board[0].size

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
        if (!isBoardCell(row = row, column = column)) {
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


    private fun revealSurroundingZeros(cellPosition: CellPosition)  {
        if (!cellPosition.isBoardCell()) {
            return
        }

        val cell = cellPosition.toCell()

        if (cell.isRevealed){
            return
        }

        cell.isRevealed = true

        if (cell.minesInArea != 0 || cell.hasBomb) {
            return
        }

        revealSurroundingZeros(CellPosition(row = cellPosition.row - 1, column = cellPosition.column - 1))
        revealSurroundingZeros(cellPosition.copy(row = cellPosition.row - 1))
        revealSurroundingZeros(cellPosition.copy(row = cellPosition.row - 1, column = cellPosition.column + 1))
        revealSurroundingZeros(cellPosition.copy(column = cellPosition.column - 1))
        revealSurroundingZeros(cellPosition.copy(column = cellPosition.column + 1))
        revealSurroundingZeros(CellPosition(row = cellPosition.row + 1, column = cellPosition.column - 1))
        revealSurroundingZeros(cellPosition.copy(row = cellPosition.row + 1))
        revealSurroundingZeros(CellPosition(row = cellPosition.row + 1, column = cellPosition.column + 1))



//        revealSurroundingZeros(row = row - 1, column = column - 1)
//        revealSurroundingZeros(row = row - 1, column = column)
//        revealSurroundingZeros(row = row - 1, column = column + 1)
//        revealSurroundingZeros(row = row,     column = column - 1)
//        revealSurroundingZeros(row = row,     column = column + 1)
//        revealSurroundingZeros(row = row + 1, column = column - 1)
//        revealSurroundingZeros(row = row + 1, column = column)
//        revealSurroundingZeros(row = row + 1, column = column + 1)
    }

    private fun isBoardCell(row: Int, column: Int)
        = row in 0..board.lastIndex && column in 0..board[0].lastIndex

    private fun CellPosition.isBoardCell()
            = isBoardCell(row = row, column = column)
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
    }

    fun hasMineAt(row: Int, column: Int): Boolean
            = mines.find { it.row == row && it.column == column } != null
}