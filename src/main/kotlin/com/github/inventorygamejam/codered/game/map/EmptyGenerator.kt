package com.github.inventorygamejam.codered.game.map

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.Random

object EmptyGenerator : ChunkGenerator() {
    override fun getFixedSpawnLocation(
        world: World,
        random: Random,
    ) = Location(world, 0.0, 0.0, 0.0)
}
