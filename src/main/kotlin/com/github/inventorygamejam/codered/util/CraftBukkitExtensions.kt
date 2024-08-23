package com.github.inventorygamejam.codered.util

import org.bukkit.World
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val World.craft get() = this as CraftWorld
val Player.craft get() = this as CraftPlayer
val ItemStack.craft get() = this as CraftItemStack