package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.broadcast
import com.github.inventorygamejam.codered.message.Messages.debug
import com.github.inventorygamejam.codered.util.mm
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent

class GameHandler(val match: GameMatch) : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if (event.player.world != match.map.world) return
        val player = event.player
        val lastDamager = (player.lastDamageCause as? EntityDamageByEntityEvent?)?.damager as? Player
        val message = if (lastDamager == null) {
            Messages.PLAYER_DEATH.replace("%s", player.name)
        } else {
            Messages.PLAYER_DEATH_BY_PLAYER.replaceFirst("%s", player.name).replaceFirst("%s", lastDamager.name)
        }
        event.deathMessage(message.mm)

        match.handleKill(player, lastDamager)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isDropItems = false

        if (event.player.world != match.map.world) return
        if (event.block.location !in match.mainObjectiveLocations) return

        val player = event.player
        if (match.isDefender(player)) {
            event.isCancelled = true
            return
        }

        if (match.currentCode != GameCode.YELLOW) {
            player.sendRichMessage(Messages.MAIN_OBJECTIVE_NOT_DESTROYABLE)
            event.isCancelled = true
            return
        }

        event.block.type = Material.AIR
        if (match.mainObjectiveLocations.all { location -> location.block.type == Material.AIR }) match.codeRed()
    }
}