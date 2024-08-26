package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.util.isOther
import com.github.inventorygamejam.codered.util.removeSingle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.VaultDisplayItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class TeamVaultHandler(val match: GameMatch) : Listener {
    private var i = 0

    @EventHandler
    fun onDisplay(event: VaultDisplayItemEvent) {
        event.displayItem = match.teamVault.items.getOrNull(i)
        if (i < match.teamVault.items.size) i++ else i = 0
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        if (!block.location.isOther(match.teamVault.location)) return
        val item = event.item ?: return
        val lootItem = match.lootItems.find { lootItem -> lootItem.item == item && !lootItem.isCollected } ?: return
        lootItem.isCollected = true
        match.teamVault.items.add(item)
        event.player.inventory.removeSingle(item)
    }
}