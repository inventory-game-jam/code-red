package com.github.inventorygamejam.codered.game.map

import com.github.inventorygamejam.codered.CodeRed.gameMapConfig
import com.github.inventorygamejam.codered.CodeRed.mapSchematic
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import net.kyori.adventure.util.TriState.FALSE
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.WorldCreator
import java.util.UUID

class GameMap {
    val world = Bukkit.createWorld(worldCreator) ?: error("failed to create world")
    val attackerSpawns = gameMapConfig.attackerSpawnPoints.map { point -> point.location().add(0.5, 0.0, 0.5) }
    val defenderSpawns = gameMapConfig.defenderSpawnPoints.map { point -> point.location().add(0.5, 0.0, 0.5) }
    val vaultLocation = gameMapConfig.vaultLocation.location()

    fun init() {
        mapSchematic.paste(BukkitAdapter.adapt(world), BlockVector3.ZERO.add(0, 128, 0))

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.FALL_DAMAGE, false)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        world.setGameRule(GameRule.KEEP_INVENTORY, true)
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)

        world.time = 1000
        world.clearWeatherDuration = 1000
    }

    fun removeSpawnPointBlocks() {
        defenderSpawns + attackerSpawns.forEach { spawn ->
            spawn.clone().subtract(0.5, 0.0, 0.5).block.type = Material.AIR

            val blockAbove = spawn.clone().add(0.0, 1.0, 0.0).block
            if (blockAbove.type != Material.AIR) blockAbove.type = Material.AIR


        }
    }

    fun remove() {
        Bukkit.unloadWorld(world, false)
        world.worldFolder.deleteRecursively()
    }

    fun BlockPoint.location() = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    fun DirectionedPrecisePoint.location() = Location(world, x, y, z, yaw, pitch)
    fun PrecisePoint.location() = Location(world, x, y, z)

    companion object {
        val worldCreator
            get() = WorldCreator(NamespacedKey("codered", "game-${UUID.randomUUID()}"))
                .biomeProvider(EmptyBiomeProvider)
                .generator(EmptyGenerator)
                .generateStructures(false)
                .keepSpawnLoaded(FALSE)
    }
}