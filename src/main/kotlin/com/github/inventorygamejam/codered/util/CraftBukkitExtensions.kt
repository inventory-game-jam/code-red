package com.github.inventorygamejam.codered.util

import org.bukkit.World
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

val World.craft get() = this as CraftWorld
val Player.craft get() = this as CraftPlayer