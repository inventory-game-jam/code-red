package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredFonts
import com.github.inventorygamejam.codered.item.gun.GunHandler
import com.github.inventorygamejam.codered.util.buildText
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object AmmoOverlay : Overlay {
    override fun render(player: Player) {
        player.sendActionBar(buildText {
            appendNegativeSpace(250)
            val currentItem = player.inventory.itemInMainHand
            val gun = GunHandler.guns[currentItem] ?: return
            val color = gun.colors().entries.find {
                (range, _) -> gun.ammo in range
            }?.value ?: error("ammo value ${gun.ammo} does not have a color")

            append("${gun.ammo}/${gun.type.maxAmmo}")
            color(color)
            font(RegisteredFonts.AMMO_OVERLAY)
        })
    }

    init {
        Bukkit.getScheduler().runTaskTimer(CodeRed, Runnable {
            val playersWithGun =
                Bukkit.getOnlinePlayers().filter { player ->
                    GunHandler.guns.contains(player.inventory.itemInMainHand)
                }

            playersWithGun.forEach(::render)
        }, 0L, 1L)
    }
}