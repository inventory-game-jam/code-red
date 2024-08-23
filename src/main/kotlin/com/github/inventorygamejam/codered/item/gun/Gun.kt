package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.item.CustomItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Gun : CustomItem() {
    override var item = ItemStack.of(Material.GOLDEN_SHOVEL)
    override val key = NamespacedKey("codered", "gun")

    override fun onLeftClick(player: Player) {}

    override fun onRightClick(player: Player) {
        Bullet(player.location.add(0.0, 1.5, 0.0), player.eyeLocation.direction)
    }

    init {
        init()
    }
}