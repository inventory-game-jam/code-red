package com.github.inventorygamejam.codered.game.map

import kotlinx.serialization.Serializable

@Serializable
data class DirectionedPrecisePoint(
    val x: Double,
    val y: Double,
    val z: Double,
    val pitch: Float,
    val yaw: Float
)
