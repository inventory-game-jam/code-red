package com.github.inventorygamejam.codered.game

import org.bukkit.Location
import org.bukkit.entity.Display
import org.bukkit.entity.Interaction
import org.bukkit.entity.ItemDisplay
import org.bukkit.inventory.ItemStack

data class LootItem(val location: Location, val item: ItemStack, var isCollected: Boolean = false) {
    val itemDisplay = location.world.spawn(location, ItemDisplay::class.java) { itemDisplay ->
        val transformation = itemDisplay.transformation
        transformation.scale.set(0.5, 0.5, 0.5)
        itemDisplay.transformation = transformation
        itemDisplay.billboard = Display.Billboard.FIXED
        itemDisplay.setItemStack(item)
    }
    val interaction = location.world.spawn(location, Interaction::class.java) { interaction ->
        interaction.isResponsive = true
    }
}
