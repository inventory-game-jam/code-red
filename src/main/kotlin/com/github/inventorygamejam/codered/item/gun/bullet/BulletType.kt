package com.github.inventorygamejam.codered.item.gun.bullet

import com.github.inventorygamejam.codered.CodeRed
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.SizedFireball
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class BulletType(
    val damage: Double,
    val velocityPerBlock: Float,
    val effectiveRange: Float,
) {
    fun createBullet(sender: Player, origin: Location, viewVector: Vector): Bullet {
        val projectile = origin.world.spawn(origin, SizedFireball::class.java) { entity ->
            entity.displayItem = BULLET_ITEM
            entity.shooter = sender
            entity.direction = viewVector
            entity.acceleration = viewVector.multiply(velocityPerBlock)
            entity.isInvisible = true
        }
        val bullet = Bullet(this, sender, projectile, origin)
        BulletHandler.bullets.add(bullet)
        return bullet
    }

    companion object {
        val BULLET_ITEM =
            ItemStack.of(Material.POPPED_CHORUS_FRUIT).apply {
                editMeta { meta ->
                    meta.setCustomModelData(3)
                }
            }
    }
}
