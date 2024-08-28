package com.github.inventorygamejam.codered.team

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class PlayerRole(val icon: ItemStack, val armor: List<ItemStack>, val items: List<ItemStack>) {
    fun equipItems(player: Player) {
        val inv = player.inventory
        inv.helmet = armor.getOrNull(0)
        inv.chestplate = armor.getOrNull(1)
        inv.leggings = armor.getOrNull(2)
        inv.boots = armor.getOrNull(3)
        player.inventory.addItem(*items.toTypedArray())
    }
}