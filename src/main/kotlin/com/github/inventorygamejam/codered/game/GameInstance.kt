package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.matchmaking.Matchmaker
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.inventorygamejam.codered.util.runTaskRepeating
import org.bukkit.Bukkit
import kotlin.random.Random
import com.github.inventorygamejam.codered.util.unregister

/**
 * A game, basically:
 * The matchmaker creates a GameInstance into which teams will be dynamically added during matchmaking
 * As soon as a certain number of full teams is reached, the 'GameInstance' counts as full
 * When it is full, it can start spinning the teams around in a round-robin tournament scheme
 */
class GameInstance(private val teamsCount: Int) {
    /**
     * Key is the team, boolean is whether the team is playing or not
     */
    private val teams: MutableMap<GameTeam, Boolean> = hashMapOf()
    private var gamesToRun: MutableMap<Pair<GameTeam, GameTeam>, Boolean> = hashMapOf()

    init {
        Matchmaker.matchGenerator.onMatchEnd { match ->
            val listeners = listOf(match.lootItemHandler, match.teamVaultHandler, match.gameHandler)
            listeners.forEach { listener -> listener.unregister()}

            teams.forEach { team ->
                if (match.attackingTeam == team.key || match.defendingTeam == team.key) {
                    teams[team.key] = false
                }
            }
        }
    }

    fun addTeam(team: GameTeam) {
        if (teams.size < teamsCount) {
            teams[team] = false
        }
    }

    fun populateGamesToRun() {
        gamesToRun.clear()
        val teamKeys = teams.keys.toList()
        teamKeys.forEach { _ ->
            for (i in teamKeys.indices) {
                for (j in i + 1 until teamKeys.size) {
                    gamesToRun[teamKeys[i] to teamKeys[j]] = false
                }
            }
        }
    }

    fun startCreatingMatches() {
        gamesToRun.forEach { (teamsPair, hasPlayedAlready) ->
            if (hasPlayedAlready) return@forEach

            runTaskRepeating {
                if (gamesToRun[teamsPair] == true) {
                    cancel()
                    return@runTaskRepeating
                }

                val (teamOne, teamTwo) = teamsPair
                val isTeamOneRunning = teams[teamOne] ?: return@runTaskRepeating
                val isTeamTwoRunning = teams[teamTwo] ?: return@runTaskRepeating

                if (!isTeamOneRunning && !isTeamTwoRunning) {
                    val isTeamOneDefending = Random.nextBoolean()
                    val match = GameMatch(
                        if (isTeamOneDefending) teamOne else teamTwo,
                        if (isTeamOneDefending) teamTwo else teamOne
                    )
                    teams[teamOne] = true
                    teams[teamTwo] = true
                    gamesToRun[teamsPair] = true

                    Matchmaker.matchGenerator.startMatch(match)
                }
            }
        }
    }

    fun isFull(): Boolean {
        return teams.size >= teamsCount
    }
}