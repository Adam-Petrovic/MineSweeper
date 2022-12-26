package com.example.minesweeper

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun isBoardSetUpProperly() {
        val board = Board(4,4,5)
        val gameBoard = board.generateBoard()
        var string = "\n"
        for(i in 0 until gameBoard.size){
            for (x in 0 until gameBoard[i].size){
                string += gameBoard[i][x]
            }
            string +="\n"
        }
        assertEquals(string, "\n1110\n1710\n1110\n0000")
    }
    @Test
    fun isMinePlacementWorking() {
        val board = Board(3,5,1)
        val gameBoard = board.generateBoard()
        for(i in 0..10){
            val mineY = (0 until gameBoard.size).random()
            val mineX = (0 until gameBoard[0].size).random()

                assertFalse("error, mineY: ${mineY} and mineX: ${mineX}", mineX >= 3 || mineY >= 5)

        }
    }
    @Test
    fun isPositionGetterWorkingTopRow() {
        val position = 31
        val board = Board(9,12,1)
        val gameBoard = board.generateBoard()
        val row : Int
        val column : Int
        if(position > gameBoard[0].size) {
            row = (position + 1) / gameBoard[0].size
            column = position - row * gameBoard[0].size
        }
        else{
            row = 0
            column = position
        }
        assertEquals(3, row)
        assertEquals(4, column)
    }

    @Test
    fun isPositionGetterWorkingLeftCorner(){
        val position = 9
        val board = Board(9,12,1)
        val gameBoard = board.generateBoard()
        var row : Int
        var column : Int
        if(position > gameBoard[0].size) {
            row = (position + 1) / gameBoard[0].size - 1
            column = position % row * gameBoard[0].size
        }

        if(position % gameBoard[0].size == 0){
            row = (position + 1) / gameBoard[0].size
            column = 0
        }

        else{
            row = 0
            column = position
        }
        assertEquals(1, row)
        assertEquals(0, column)
    }
}