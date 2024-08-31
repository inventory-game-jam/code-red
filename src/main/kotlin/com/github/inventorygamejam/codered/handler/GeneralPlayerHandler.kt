package com.github.inventorygamejam.codered.handler

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.sendPack
import com.github.inventorygamejam.codered.util.APITeam
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object GeneralPlayerHandler : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.sendPack()

        var team: APITeam? = null

        CodeRed.apiTeams.forEach { apiTeam ->
            apiTeam.players.forEach { apiPlayer ->
                if(apiPlayer.uuid == player.uniqueId) team = apiTeam
            }
        }

        if(team == null) return
        val gameTeam = CodeRed.gameTeams.find { it.name == team!!.name }
        if(gameTeam == null) {
            CodeRed.logger.severe("GameTeam ${team!!.name} not found!")
            return
        }
        gameTeam.uuids.add(player.uniqueId)
        CodeRed.logger.info("Player ${player.name} from team ${team!!.name} joined")
    }
}