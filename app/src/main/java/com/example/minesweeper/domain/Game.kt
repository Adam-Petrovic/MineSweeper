package com.example.minesweeper.domain

class Game {

    private lateinit var gameBoard : GameBoard

    fun initialize(width : Int, height : Int, numOfMines : Int) {
        gameBoard = GameBoard(width = width, height = height, numOfMines = numOfMines)
    }

    fun revealCellAt(position: Int) {
        gameBoard.revealCellAt(position)
    }

    fun getBoard() = gameBoard
}