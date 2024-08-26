package com.github.inventorygamejam.codered.matchmaking

import com.github.inventorygamejam.codered.game.GameInstance
import com.github.inventorygamejam.codered.team.GameTeam

object Matchmaker {
    const val TEAMS_PER_GAME = 8
    val matchGenerator: MatchGenerator = MatchGenerator()

    private val teams: MutableList<GameTeam> = mutableListOf()
    private var gameInstance: GameInstance = GameInstance(TEAMS_PER_GAME)

    fun addTeam(team: GameTeam) {
        if(teams.size + 1 >= TEAMS_PER_GAME) {
            teams.forEach(gameInstance::addTeam)
            gameInstance.populateGamesToRun()
            gameInstance.startCreatingMatches()

            teams.clear()
            return
        }
        teams.add(team)
    }
}