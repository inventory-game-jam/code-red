package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredFonts
import com.github.inventorygamejam.codered.item.gun.AmmoManager
import com.github.inventorygamejam.codered.item.gun.AmmoManager.MAX_AMMO
import com.github.inventorygamejam.codered.item.gun.Gun
import com.github.inventorygamejam.codered.util.buildText
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object AmmoOverlay : Overlay {
    override fun render(player: Player) {
        player.sendActionBar(buildText {
            appendNegativeSpace(250)
            val ammo = AmmoManager[player]
            val color = AmmoManager.COLORS.entries.find { (range, _) -> ammo in range }?.value ?: error("ammo value $ammo does not have a color")

            append("$ammo/$MAX_AMMO")
            color(color)
            font(RegisteredFonts.AMMO_OVERLAY)
        })
    }

    init {
        Bukkit.getScheduler().runTaskTimer(CodeRed, Runnable {
            val playersWithGun =
                Bukkit.getOnlinePlayers().filter { player ->
                    player.inventory.itemInMainHand == Gun.item
                }

            playersWithGun.forEach(::render)
        }, 0L, 1L)
    }
}