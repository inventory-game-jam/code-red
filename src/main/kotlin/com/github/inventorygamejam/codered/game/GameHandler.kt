package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.item.gun.bullet.BulletHandler
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.util.mm
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent

class GameHandler(val match: GameMatch) : Listener {
    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.entity.world != match.map.world) return
        val player = event.entity as? Player ?: return

        if (event.damager.uniqueId !in BulletHandler.uuids) return

        val bullet = BulletHandler.bullets.find { bullet -> bullet.projectile.uniqueId == event.damager.uniqueId }
            ?: error("damaged by nonexistant bullet?")
        if (
            (match.isDefender(bullet.sender) && match.isDefender(player)) ||
            (match.isAttacker(bullet.sender) && match.isAttacker(player))
        ) {
            event.isCancelled = true
        }
    }

    @Suppress("DEPRECATION", "REMOVAL")
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
        player.lastDamageCause = null

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

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (event.player.world != match.map.world) return

        event.isCancelled = !match.hasStarted && event.hasExplicitlyChangedBlock()
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.player.world != match.map.world) return

        event.isCancelled = !match.hasStarted
    }
}