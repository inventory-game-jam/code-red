package com.github.inventorygamejam.codered.util

import org.bukkit.Location

fun Location.isOther(other: Location): Boolean {
    return x == other.x && y == other.y && z == other.z && world == other.world
}