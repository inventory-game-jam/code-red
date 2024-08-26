package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.item.gun.FireballBullet.Companion.MAX_SQUARED_DISTANCE
import com.github.inventorygamejam.codered.util.registerEvents
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.event.entity.ProjectileHitEvent

object BulletHandler : Listener {
    val bullets = mutableListOf<FireballBullet>()
    val projectiles get() = bullets.map(FireballBullet::projectile)
    val uuids get() = projectiles.map(Entity::getUniqueId)

    init {
        registerEvents(this)
    }

    @EventHandler
    fun onHit(event: ProjectileHitEvent) {
        if (event.entity.uniqueId in projectiles.map(Entity::getUniqueId)) return

        event.hitEntity?.let { entity ->
            val livingEntity = entity as? LivingEntity ?: return@let
            livingEntity.damage(15.0)
        }

        event.isCancelled = true
        Bukkit.getScheduler().runTaskLater(CodeRed, Runnable { event.entity.remove() }, 40L)
    }

    @EventHandler
    fun onExplosionPrime(event: ExplosionPrimeEvent) {
        event.isCancelled = event.entity.uniqueId in uuids
    }

    @EventHandler
    fun onExplode(event: EntityExplodeEvent) {
        event.isCancelled = event.entity.uniqueId in uuids
    }

    @EventHandler
    fun onMove(event: EntityMoveEvent) {
        if (event.entity.uniqueId !in uuids) return
        val bullet = bullets.find { it.projectile.uniqueId == event.entity.uniqueId } ?: return

        if (bullet.origin.distanceSquared(bullet.projectile.location) > MAX_SQUARED_DISTANCE) {
            bullet.projectile.remove()
        }
    }
}