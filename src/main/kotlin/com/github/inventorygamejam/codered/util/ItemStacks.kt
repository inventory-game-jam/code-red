package com.github.inventorygamejam.codered.util

import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.inventory.ItemStack

fun ItemStack.name(displayName: String) = apply {
    editMeta { meta ->
        meta.displayName(displayName.mm.decoration(TextDecoration.ITALIC, false))
    }
}