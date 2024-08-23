package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.util.craft
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.papermc.paper.event.entity.EntityMoveEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
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
    init {
        Bukkit.getPluginManager().registerSuspendingEvents(this, CodeRed)

        accelerationPower = 0.0
        assignDirectionalMovement(Vec3(viewVector.x, viewVector.y, viewVector.z), accelerationPower)

        origin.world.craft.handle.addFreshEntity(this)
    }

    // TODO: team and friendly fire checking
    override fun canHitEntity(entity: Entity): Boolean = super.canHitEntity(entity)

    override fun onHitEntity(entityHitResult: EntityHitResult) {
        Bukkit.broadcast(text("Entity ${entityHitResult.entity.name.string} got hit by wind charge"))
    }

    override fun explode(pos: Vec3?) {
        HandlerList.unregisterAll(this)
    }

    @EventHandler
    fun onMove(event: EntityMoveEvent) {
        val entity = event.entity
        if (entity.uniqueId != uuid) return
        if (origin.distanceSquared(event.to) >= MAX_DISTANCE_SQUARED) {
            remove(RemovalReason.KILLED)
            HandlerList.unregisterAll(this)
        }
    }

    companion object {
        const val MAX_DISTANCE_SQUARED = 20
    }
}
