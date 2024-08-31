package com.github.inventorygamejam.codered.item.gun

import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.inventory.ItemStack
import kotlin.math.roundToInt

class Gun(val type: GunType, var ammo: Int, val item: ItemStack) {
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