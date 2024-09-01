package com.github.inventorygamejam.codered.item.gun

import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.inventory.ItemStack
import kotlin.math.roundToInt

class Gun(val type: GunType, var ammo: Int, val item: ItemStack) {
    var reloadingTicks = 0
    val hasAmmo get() = ammo > 0
    val isReloading get() = reloadingTicks > 0

    fun startReload() {
        ammo = 0
        reloadingTicks = type.wholeMagazineReload
    }

    fun colors(): Map<IntRange, TextColor> {
        val full = type.maxAmmo
        val twoThirds = (full * 2 / 3.0).roundToInt()
        val oneThird = (full / 3.0).roundToInt()

        return mapOf(
            (twoThirds..full) to GREEN,
            (oneThird..twoThirds) to YELLOW,
            (0..oneThird) to RED
        )
    }
}