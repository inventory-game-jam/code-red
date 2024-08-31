package com.github.inventorygamejam.codered.team

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class PlayerRole(val icon: ItemStack, val armor: List<ItemStack>, val items: List<ItemStack>) {
    fun equipItems(player: Player) {
        val inventory = player.inventory
        inventory.helmet = armor.getOrNull(0)
        inventory.chestplate = armor.getOrNull(1)
        inventory.leggings = armor.getOrNull(2)
        inventory.boots = armor.getOrNull(3)
        player.inventory.addItem(*items.toTypedArray())
    }
}