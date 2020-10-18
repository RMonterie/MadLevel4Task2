package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.database.GameRoomDatabase
import com.example.madlevel4task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game>{
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game){
        return gameDao.insertGame(game)
    }

    suspend fun deleteAllGames(){
        return gameDao.deleteAllGames()
    }
}