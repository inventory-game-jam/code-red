package com.github.inventorygamejam.codered.item

import com.github.inventorygamejam.codered.CodeRed
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class CustomItem : Listener {
    abstract val key: NamespacedKey
    abstract var item: ItemStack
    abstract fun onRightClick(player: Player)
    abstract fun onLeftClick(player: Player)

    fun init() {
        Bukkit.getPluginManager().registerEvents(this, CodeRed)
        item = item.apply {
            itemMeta = itemMeta.apply {
                persistentDataContainer.set(
                    TYPE_KEY, PersistentDataType.STRING, key.toString()
                )
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (item.itemMeta.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let { NamespacedKey.fromString(it) } == key
            && event.action != Action.PHYSICAL
        ) {
            if (event.action in listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)) onRightClick(player)
            else onLeftClick(player)
        }
    }

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent) {
        if (item.itemMeta.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let { NamespacedKey.fromString(it) } == key
        ) {
            event.isCancelled = true
        }
    }

    companion object {
        val TYPE_KEY = NamespacedKey("codered", "custom_item_type")
    }
}