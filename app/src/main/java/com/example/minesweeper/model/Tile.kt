package com.example.minesweeper.model

import androidx.annotation.DrawableRes


data class Tile(@DrawableRes val tileResourceId : Int,
                @DrawableRes val numberResourceId : Int,
                @DrawableRes val flagResourceId : Int)