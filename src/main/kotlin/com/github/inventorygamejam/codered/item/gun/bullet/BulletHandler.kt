package com.github.inventorygamejam.codered.item.gun.bullet

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.util.registerEvents
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.event.entity.ProjectileHitEvent

object BulletHandler : Listener {
    val bullets = mutableListOf<Bullet>()
    val projectiles get() = bullets.associateBy { it.projectile }
    val uuids get() = bullets.map { it.projectile.uniqueId }

    init {
        registerEvents(this)
    }

    @EventHandler
    fun onHit(event: ProjectileHitEvent) {
        if (event.entity.uniqueId in uuids) return
        val bullet = projectiles[event.entity] ?: error("this isn't even possible lol")

        event.hitEntity?.let { entity ->
            val livingEntity = entity as? LivingEntity ?: return@let
            livingEntity.damage(bullet.type.damage)
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

        if (bullet.origin.distanceSquared(bullet.projectile.location) > bullet.type.effectiveRange) {
            bullet.projectile.remove()
            bullets.remove(bullet)
        }
    }
}