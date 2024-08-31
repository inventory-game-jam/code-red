package com.github.inventorygamejam.codered.util

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.removeSingle(item: ItemStack) {
    forEachIndexed { i, stack ->
        if (item == stack) {
            setItem(i, null)
            return
        }
    }
}