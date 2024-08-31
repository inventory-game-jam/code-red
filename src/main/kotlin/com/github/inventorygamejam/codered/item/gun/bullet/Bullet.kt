package com.github.inventorygamejam.codered.item.gun.bullet

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile

data class Bullet(val type: BulletType, val sender: Player, val projectile: Projectile, val origin: Location)