package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.item.CustomItem.Companion.TYPE_KEY
import com.github.inventorygamejam.codered.util.buildText
import com.github.inventorygamejam.codered.util.registerEvents
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.NamespacedKey.fromString
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.math.roundToInt

object GunHandler : Listener {
    val guns = mutableMapOf<ItemStack, Gun>()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action.isRightClick) {
            val gun = guns[event.item] ?: return
            if (!gun.hasAmmo || gun.isReloading) return
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

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val item = event.offHandItem
        val gun = guns[item] ?: return

        event.isCancelled = true

        if (gun.isReloading || gun.ammo == gun.type.maxAmmo) return

        gun.startReload()
    }

    @EventHandler
    fun onItemChange(event: PlayerItemHeldEvent) {
        val player = event.player
        val previousStack = player.inventory.getItem(event.previousSlot)
        val previousGun = guns[previousStack]
        val newStack = player.inventory.getItem(event.newSlot)
        val newGun = guns[newStack]

        if (previousGun != null && newGun == null) previousGun.type.showScope(false, player)
        if (previousGun == null && newGun != null) newGun.type.showScope(true, player)
    }

    init {
        registerEvents(this)
        Bukkit.getScheduler().runTaskTimer(CodeRed, { _ ->
            guns.forEach { (_, gun) ->
                if (!gun.isReloading) return@forEach
                val reloadPercent =
                    (gun.type.wholeMagazineReload - gun.reloadingTicks) / gun.type.wholeMagazineReload.toDouble()
                gun.ammo = (reloadPercent * gun.type.maxAmmo).roundToInt()
                gun.reloadingTicks--
            }
        }, 0, 1)
    }
}