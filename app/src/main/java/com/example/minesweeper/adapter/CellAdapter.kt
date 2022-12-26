package com.example.minesweeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.Board
import com.example.minesweeper.MainActivity
import com.example.minesweeper.R
import com.example.minesweeper.data.Datasource
import com.example.minesweeper.domain.GameBoard
import com.example.minesweeper.model.Tile

@Suppress("IMPLICIT_CAST_TO_ANY")
class CellAdapter(
    private val context: MainActivity,
    private val gameBoard: GameBoard,
) : RecyclerView.Adapter<CellAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val cell : ImageButton = view.findViewById(R.id.cellButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_cell, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CellAdapter.ItemViewHolder, position: Int) {
        val cellBackground = if (position % 2 == 1) R.drawable.tile1 else R.drawable.tile2
        val cellState = gameBoard.cellAt(position)

        val cellImage = if (cellState.isRevealed) {
            if (cellState.hasBomb) {
                R.drawable.explosion
            } else {
                when(cellState.minesInArea) {
                    0 -> R.drawable.zero
                    1 -> R.drawable.one
                    2 -> R.drawable.two
                    3 -> R.drawable.three
                    4 -> R.drawable.four
                    5 -> R.drawable.five
                    6 -> R.drawable.six
                    else -> cellBackground
                }
            }
        } else {
            if (cellState.isFlagged) {
                R.drawable.flag
            } else {
                cellBackground
            }
        }

        holder.cell.setImageResource(cellImage)

        holder.cell.setOnLongClickListener {
            cellOnLongClick(it, position)

        }
        holder.cell.setOnClickListener {
            cellOnClick(it, position)
        }
    }

    override fun getItemCount() = gameBoard.cellCount()


    private fun cellOnLongClick(view: View, position: Int): Boolean {
        gameBoard.placeFlagAt(position)
        notifyDataSetChanged()
        return true
    }
    private fun cellOnClick(view: View, position: Int): Boolean {
        gameBoard.revealCellAt(position)
        notifyDataSetChanged()
        return true
    }


}