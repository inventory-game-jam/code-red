package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.util.registerEvents
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack

object GunHandler : Listener {
    val guns = mutableMapOf<ItemStack, Gun>()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action.isRightClick) {
            val gun = guns[event.item] ?: return
            if (gun.ammo <= 0) return
            if (event.player.hasCooldown(gun.item.type)) return
            gun.type.shoot(event.player, gun.item)
            gun.ammo--
        } else {
            // do something on left click
        }
    }

    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        if (event.player.isFlying) return
        val itemInHand = event.player.inventory.itemInMainHand
        val gun = guns[itemInHand] ?: return
        gun.type.showScope(event.isSneaking, event.player)
    }

    init {
        registerEvents(this)
    }
}