package com.github.inventorygamejam.codered.team

import org.bukkit.Bukkit
import org.bukkit.scoreboard.Team
import java.util.UUID

data class GameTeam(val name: String, val uuids: MutableList<UUID> = mutableListOf()) {
    val players get() = uuids.mapNotNull(Bukkit::getPlayer)
    val scoreboardTeam: Team

    init {
        val board = Bukkit.getScoreboardManager().mainScoreboard
        scoreboardTeam = board.getTeam(name) ?: board.registerNewTeam(name)
        uuids.forEach { uuid -> scoreboardTeam.addEntry(Bukkit.getOfflinePlayer(uuid).name.toString()) }
    }
}