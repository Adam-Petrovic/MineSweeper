package com.example.minesweeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.R
import com.example.minesweeper.domain.BoardCell
import com.example.minesweeper.domain.GameBoard

@Suppress("IMPLICIT_CAST_TO_ANY")
class CellAdapter(
    private val gameBoard: GameBoard,
) : RecyclerView.Adapter<CellAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cell : ImageButton = view.findViewById(R.id.cellButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_cell, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cellBackground = if (position % 2 == 1) R.drawable.tile1 else R.drawable.tile2
        val cellState = gameBoard.cellAt(position)
        val cellImage = cellState.toCellDrawable(cellBackground)

        holder.cell.setImageResource(cellImage)

        holder.cell.setOnLongClickListener {
            cellOnLongClick(position)

        }
        holder.cell.setOnClickListener {
            cellOnClick(position)
        }
    }

    override fun getItemCount() = gameBoard.cellCount()

    private fun BoardCell.toCellDrawable(cellBackground: Int): Int {
        return if (isRevealed) {
            if (hasBomb) {
                R.drawable.explosion
            } else {
                when(minesInArea) {
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
            if (isFlagged) {
                R.drawable.flag
            } else {
                cellBackground
            }
        }
    }
    private fun cellOnLongClick(position: Int): Boolean {
        gameBoard.placeFlagAt(position)
        notifyDataSetChanged()
        return true
    }
    private fun cellOnClick(position: Int): Boolean {
        gameBoard.revealCellAt(position)
        notifyDataSetChanged()
        return true
    }
}