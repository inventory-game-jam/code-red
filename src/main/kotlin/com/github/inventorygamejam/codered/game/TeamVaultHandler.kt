package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.message.Messages
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
        val items = match.teamVault.items
        if (i > items.size - 1) i = 0
        event.displayItem = match.teamVault.items.getOrNull(i)
        if (i < match.teamVault.items.size) i++ else i = 0
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (match.isAttacker(event.player)) return
        val block = event.clickedBlock ?: return
        if (!block.location.isOther(match.teamVault.location)) return
        val item = event.item ?: return
        val lootItem = match.lootItems.find { lootItem -> lootItem.item.type == item.type && !lootItem.isCollected } ?: return
        lootItem.isCollected = true
        match.teamVault.items.add(item)
        item.amount--
        event.player.sendRichMessage(Messages.ITEM_INSERTED)

        if (match.lootItems.all { lootItem -> lootItem.isCollected }) match.codeYellow()
    }
}