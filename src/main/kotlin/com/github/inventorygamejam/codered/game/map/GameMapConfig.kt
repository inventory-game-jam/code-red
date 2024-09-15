package com.github.inventorygamejam.codered.game.map

import kotlinx.serialization.Serializable

@Serializable
data class GameMapConfig(
    val attackerSpawnPoints: List<DirectionedPrecisePoint>,
    val defenderSpawnPoints: List<DirectionedPrecisePoint>,
    val vaultLocation: BlockPoint,
    val corners: List<BlockPoint>
)
