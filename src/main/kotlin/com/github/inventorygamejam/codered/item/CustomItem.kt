package com.github.inventorygamejam.codered.item

import com.github.inventorygamejam.codered.util.registerEvents
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class CustomItem : Listener {
    abstract val key: NamespacedKey
    abstract var item: ItemStack
    abstract fun onRightClick(player: Player)
    abstract fun onLeftClick(player: Player)
    abstract fun onCrouchChange(player: Player, value: Boolean)
    abstract val isOffhandable: Boolean

    fun init() {
        registerEvents(this)
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
        val item = player.inventory.itemInMainHand

        if (item.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
            && event.action != Action.PHYSICAL
        ) {
            event.isCancelled = true

            if (event.action in listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)) onRightClick(player)
            else onLeftClick(player)
        }
    }

    @EventHandler
    fun onCrouchChange(event: PlayerToggleSneakEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
        ) {
            if (player.isFlying) return
            onCrouchChange(player, event.isSneaking)
        }
    }

    @EventHandler
    fun onItemChange(event: PlayerItemHeldEvent) {
        val player = event.player
        val previousStack = player.inventory.getItem(event.previousSlot)
        val newStack = player.inventory.getItem(event.newSlot)

        if (previousStack?.persistentDataContainer?.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
        ) {
            onCrouchChange(player, false)
        }

        if (newStack?.persistentDataContainer?.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
            && player.isSneaking
        ) {
            onCrouchChange(player, true)
        }
    }

    @EventHandler
    fun onToggleFlight(event: PlayerToggleFlightEvent) {
        val player = event.player
        if (!player.isSneaking) return

        if (player.inventory.itemInMainHand.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
        ) {
            onCrouchChange(player, false)
        }
    }

    @EventHandler
    fun onSwapHands(event: PlayerSwapHandItemsEvent) {
        if (event.mainHandItem.persistentDataContainer.get(TYPE_KEY, PersistentDataType.STRING)
                ?.let(NamespacedKey::fromString) == key
            && !isOffhandable
        ) {
            event.isCancelled = true
        }
    }

    companion object {
        val TYPE_KEY = NamespacedKey("codered", "custom_item_type")
    }
}