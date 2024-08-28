package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.message.Messages
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class LootItemHandler(val match: GameMatch) : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (match.isAttacker(player)) return
        val entity = event.rightClicked

        if (entity.type != EntityType.INTERACTION) return
        val lootItem = match.lootItems.find { lootItem -> lootItem.interaction == entity } ?: return

        lootItem.interaction.remove()
        lootItem.itemDisplay.remove()
        player.inventory.addItem(lootItem.item)
        event.player.sendRichMessage(Messages.ITEM_PICKED_UP)
    }
}