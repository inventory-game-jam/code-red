package com.github.inventorygamejam.codered.game.map

import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo

object EmptyBiomeProvider : BiomeProvider() {
    override fun getBiome(
        worldInfo: WorldInfo,
        x: Int,
        y: Int,
        z: Int,
    ) = Biome.THE_VOID

    override fun getBiomes(worldInfo: WorldInfo) = emptyList<Biome>()
}
