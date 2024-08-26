package com.github.inventorygamejam.codered.game

import org.bukkit.entity.EntityType
import org.bukkit.entity.Interaction
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class LootItemHandler(val match: GameMatch) : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (match.isDefender(player)) return
        val entity = event.rightClicked

        if (entity.type != EntityType.INTERACTION) return
        val lootItem = match.lootItems.find { lootItem -> lootItem.interaction == entity } ?: return

        lootItem.interaction.remove()
        lootItem.itemDisplay.remove()
        player.inventory.addItem(lootItem.item)
    }

    @EventHandler
    fun onHit(event: PlayerInteractEvent) {
        if (event.action !in listOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)) return
        val player = event.player
        if (match.isDefender(player)) return
        val hitLocation = event.interactionPoint ?: return
        val interaction =
            hitLocation.getNearbyEntitiesByType<Interaction>(Interaction::class.java, 0.5).firstOrNull() ?: return

        val lootItem = match.lootItems.find { lootItem -> lootItem.interaction == interaction } ?: return
        lootItem.interaction.remove()
        lootItem.itemDisplay.remove()
        player.inventory.addItem(lootItem.item)
    }
}