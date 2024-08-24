package com.github.inventorygamejam.codered.item.gun

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.SizedFireball
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class FireballBullet(
    val sender: Player,
    val origin: Location,
    val viewVector: Vector,
) : Listener {
    val projectile =
        origin.world.spawn(origin, SizedFireball::class.java) { entity ->
            entity.displayItem = BULLET_ITEM
            entity.shooter = sender
            entity.direction = viewVector
            entity.acceleration = viewVector.multiply(10)
        }

    init {
        BulletHandler.bullets.add(this)
    }

    companion object {
        val BULLET_ITEM =
            ItemStack.of(Material.POPPED_CHORUS_FRUIT).apply {
                editMeta { meta ->
                    meta.setCustomModelData(1)
                }
            }

        const val MAX_SQUARED_DISTANCE = 50
    }
}
