package com.github.inventorygamejam.codered.game.map

import com.github.inventorygamejam.codered.CodeRed.gameMapConfig
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import net.kyori.adventure.util.TriState.FALSE
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.Material.BLUE_STAINED_GLASS
import org.bukkit.Material.RED_STAINED_GLASS
import org.bukkit.NamespacedKey
import org.bukkit.WorldCreator
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import java.io.File
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

class GameMap {
    val world = Bukkit.createWorld(worldCreator) ?: error("failed to create world")
    private val structureManager = Bukkit.getServer().structureManager
    val floorSchematic = structureManager.loadStructure(File(assetPath, "schematics/floor.nbt"))
    val buildingFiles = File(assetPath, "schematics/buildings").listFiles()?.toList() ?: emptyList()
    val buildings = buildingFiles.map { file -> structureManager.loadStructure(file) }
    val attackerSpawns = gameMapConfig.attackerSpawnPoints.map { point -> point.location() }
    val defenderSpawns = gameMapConfig.defenderSpawnPoints.map { point -> point.location() }
    private val random = ThreadLocalRandom.current()

    fun init() {
        floorSchematic.place(
            Location(world, 0.0, 64.0, 0.0),
            true,
            StructureRotation.NONE,
            Mirror.NONE,
            0,
            1f,
            random
        )

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.FALL_DAMAGE, false)
        world.time = 1000
        world.clearWeatherDuration = 1000
    }

    fun placeSpawnPointBlocks() {
        defenderSpawns.forEach { spawn ->
            spawn.clone().add(0.0, 1.0, 0.0).block.type = BLUE_STAINED_GLASS
        }
        attackerSpawns.forEach { spawn ->
            spawn.clone().add(0.0, 1.0, 0.0).block.type = RED_STAINED_GLASS
        }
    }

    fun placeBuildings() {
        gameMapConfig.buildings.forEach { position ->
            val building = buildings.random()
            building.place(
                position.location(),
                true,
                StructureRotation.NONE,
                Mirror.NONE,
                0,
                1f,
                random
            )
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
        val worldCreator get() = WorldCreator(NamespacedKey("codered", "game-${UUID.randomUUID()}"))
            .biomeProvider(EmptyBiomeProvider)
            .generator(EmptyGenerator)
            .generateStructures(false)
            .keepSpawnLoaded(FALSE)
    }
}