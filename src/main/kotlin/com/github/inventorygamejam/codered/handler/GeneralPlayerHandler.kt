package com.github.inventorygamejam.codered.handler

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.CodeRed.apiTeams
import com.github.inventorygamejam.codered.CodeRed.gameTeams
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.sendPack
import com.github.inventorygamejam.codered.util.APIPlayer
import com.github.inventorygamejam.codered.util.APITeam
import net.minecraft.commands.arguments.TeamArgument.team
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object GeneralPlayerHandler : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.sendPack()

        var team = apiTeams.find { team -> player.uniqueId in team.players.map(APIPlayer::uuid) }

        if (team == null) return
        val gameTeam = gameTeams.find { gameTeam -> gameTeam.name == team.name }
        if (gameTeam == null) {
            CodeRed.run { logger.severe("GameTeam ${team.name} not found!") }
            return
        }
        gameTeam.uuids.add(player.uniqueId)
        CodeRed.logger.info("Player ${player.name} from team ${team.name} joined")
    }
}