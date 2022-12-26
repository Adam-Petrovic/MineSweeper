package com.example.minesweeper.domain

class BoardCell(
    val hasBomb: Boolean,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false,
    var minesInArea : Int = 0,
)