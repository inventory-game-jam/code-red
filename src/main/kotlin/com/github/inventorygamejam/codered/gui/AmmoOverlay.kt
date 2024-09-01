package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredFonts
import com.github.inventorygamejam.codered.item.gun.GunHandler
import com.github.inventorygamejam.codered.util.buildText
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.keybind
import net.kyori.adventure.text.KeybindComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.eclipse.jgit.diff.Subsequence.a

object AmmoOverlay : Overlay {
    override fun render(player: Player) {
        player.sendActionBar(buildText {
            val currentItem = player.inventory.itemInMainHand
            val gun = GunHandler.guns[currentItem] ?: return
            val color = gun.colors().entries.find {
                (range, _) -> gun.ammo in range
            }?.value ?: error("ammo value ${gun.ammo} does not have a color")

            val ammoText = "${gun.ammo}/${gun.type.maxAmmo}"
            val ammoTextWidth = RegisteredFonts.AMMO_OVERLAY.width(ammoText)
            appendWithOffset(200) {
                append(ammoText)
                color(color)
                font(RegisteredFonts.AMMO_OVERLAY)
            }

            if (gun.isReloading) {
                appendWithOffset(167 - ammoTextWidth) {
                    gray()
                    append("RELOADING")
                    font(RegisteredFonts.AMMO_OVERLAY_SUB)
                }
            } else if (!gun.hasAmmo) {
                appendWithOffset(151 - ammoTextWidth) {
                    gray()
                    append(keybind("key.swapOffhand"))
                    append(" TO RELOAD")
                    font(RegisteredFonts.AMMO_OVERLAY_SUB)
                }
            } else {
                appendWithOffset(160 - ammoTextWidth) { appendSpace(RegisteredFonts.AMMO_OVERLAY_SUB.width("RELOADING")) }
            }
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