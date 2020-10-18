package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        btnToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_historyFragment)
        }

        ibRock.setOnClickListener {
            decideGameResult("rock")
        }
        ibPaper.setOnClickListener {
            decideGameResult("paper")
        }
        ibScissors.setOnClickListener {
            decideGameResult("scissors")
        }
    }

    /**
     * Function to decide the result of a played game
     *
     * @param playerThrow the string value of the throw the player made
     */
    private fun decideGameResult(playerThrow: String) {
        var computerThrow = generateComputerThrow()
        var gameResult: String = compareThrows(playerThrow, computerThrow)
        updateMoveImages(playerThrow, computerThrow)
        tvCurrentGameResult.text = gameResult
        addGameToHistory(playerThrow, computerThrow, gameResult)
    }


    /**
     * Function to generate a random throw
     *
     * @return the randomly selected index of possibleThrows
     */
    private fun generateComputerThrow(): String {
        val possibleThrows = arrayOf("rock", "paper", "scissors")
        val randomInteger = (0..2).shuffled().first()

        return possibleThrows[randomInteger]
    }


    /**
     * Function to compare the throws the player and the computer have made
     *
     * @return string value of the result of the match
     */
    private fun compareThrows(playerThrow: String, computerThrow: String): String{
        if(playerThrow == computerThrow){
            return "draw"
        }
        if(playerThrow == "rock" && computerThrow == "scissors"){
            return "win"
        } else if(playerThrow == "paper" && computerThrow == "rock"){
            return "win"
        } else if(playerThrow == "scissors" && computerThrow == "paper"){
            return "win"
        }
        return "loss"
    }

    /**
     * Function to update the imageViews on the game fragment
     *
     * @param playerThrow string value of the player's throw
     * @param computerThrow string value of the computer's throw
     */
    private fun updateMoveImages(playerThrow: String, computerThrow: String){
        when(playerThrow){
            "rock" -> ivCurrentPlayerThrow.setImageResource(R.drawable.rock)
            "paper" -> ivCurrentPlayerThrow.setImageResource(R.drawable.paper)
            "scissors" -> ivCurrentPlayerThrow.setImageResource(R.drawable.scissors)
        }
        when(computerThrow){
            "rock" -> ivCurrentComputerThrow.setImageResource(R.drawable.rock)
            "paper" -> ivCurrentComputerThrow.setImageResource(R.drawable.paper)
            "scissors" -> ivCurrentComputerThrow.setImageResource(R.drawable.scissors)
        }
    }

    /**
     * Function to add game to room database
     *
     * @param playerThrow
     * @param computerThrow
     * @param result result of the played game
     */
    private fun addGameToHistory(playerThrow: String, computerThrow: String, result: String){
        mainScope.launch {
            val game = Game(
                computerMove = computerThrow,
                playerMove = playerThrow,
                result = result
            )
            withContext(Dispatchers.IO){
                gameRepository.insertGame(game)
            }
        }
    }
}