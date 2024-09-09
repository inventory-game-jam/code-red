package com.github.inventorygamejam.codered.util

import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.withName(displayName: String) = editItemMeta {
    displayName(displayName.mm.decoration(TextDecoration.ITALIC, false))
}

fun ItemStack.editItemMeta(block: ItemMeta.() -> Unit) = apply { editMeta { block(it) } }

inline fun <reified T : ItemMeta> ItemStack.editItemMetaSpecific(crossinline block: T.() -> Unit) = apply { editMeta(T::class.java) { block(it) } }

fun ItemStack.withoutTooltip() = editItemMeta {
    isHideTooltip = true
}

fun ItemStack.withDestroyables(vararg materials: Material) = editItemMeta {
    @Suppress("redundantsuppression", "deprecation", "removal")
    canDestroy = materials.toSet()
}

fun ItemStack.withUnbreakable() = editItemMeta {
    isUnbreakable = true
}

fun ItemStack.withCustomModelData(value: Int) = editItemMeta { setCustomModelData(value) }