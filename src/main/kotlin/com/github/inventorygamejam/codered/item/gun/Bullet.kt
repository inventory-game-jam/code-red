package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.util.craft
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.papermc.paper.event.entity.EntityMoveEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.minecraft.world.entity.Display
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

// Please forgive me.
class Bullet(
    val origin: Location,
    val viewVector: Vector,
) : WindCharge(
    origin.world.craft.handle,
    origin.x,
    origin.y,
    origin.z,
    Vec3(0.0, 0.0, 0.0),
), Listener {
    val itemDisplay = Display.ItemDisplay(EntityType.ITEM_DISPLAY, origin.world.craft.handle).apply {
        itemStack = ItemStack.of(Material.POPPED_CHORUS_FRUIT).craft.handle
    }

    init {
        Bukkit.getPluginManager().registerSuspendingEvents(this, CodeRed)
        val level = origin.world.craft.handle

        accelerationPower = ACCELERATION
        assignDirectionalMovement(Vec3(viewVector.x, viewVector.y, viewVector.z), accelerationPower)
        
        level.addFreshEntity(this)
        itemDisplay.teleportTo(origin.x, origin.y, origin.z)
        itemDisplay.startRiding(this, true)
        level.addFreshEntity(itemDisplay)
    }

    // TODO: team and friendly fire checking
    override fun canHitEntity(entity: Entity): Boolean = super.canHitEntity(entity)

    override fun onHitEntity(entityHitResult: EntityHitResult) {
        val entity = entityHitResult.entity
        Bukkit.broadcast(text("Entity ${entity.name.string} got hit"))

        entity.hurt(entity.damageSources().genericKill(), 5f)

        remove()
    }

    override fun explode(pos: Vec3?) {
        remove()
    }

    @EventHandler
    fun onMove(event: EntityMoveEvent) {
        val entity = event.entity
        if (entity.uniqueId != uuid) return
        if (origin.distanceSquared(event.to) >= MAX_DISTANCE_SQUARED) remove()
    }

    fun remove() {
        HandlerList.unregisterAll(this)
        remove(RemovalReason.KILLED)
        itemDisplay.remove(RemovalReason.KILLED)
    }

    companion object {
        const val MAX_DISTANCE_SQUARED = 30
        const val ACCELERATION = 2.0
    }
}
