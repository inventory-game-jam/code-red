package com.github.inventorygamejam.codered.gamemap

import kotlinx.serialization.Serializable

@Serializable
data class GameMapConfig(
    val attackerSpawnPoints: List<DirectionedPrecisePoint>,
    val defenderSpawnPoints: List<DirectionedPrecisePoint>,
    val buildings: List<BlockPoint>,
)
