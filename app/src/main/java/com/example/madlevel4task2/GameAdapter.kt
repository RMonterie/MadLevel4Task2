package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private val games: List<Game>): RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun databind(game: Game){
            itemView.tvMatchResult.text = game.result
            itemView.tvComputerPlay.text = game.computerMove
            itemView.tvPlayerPlay.text = game.playerMove
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        return holder.databind(games[position])
    }

    override fun getItemCount(): Int {
       return games.size
    }
}