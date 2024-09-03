package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.broadcast
import com.github.inventorygamejam.codered.util.mm
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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
    }
}