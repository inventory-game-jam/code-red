package com.github.inventorygamejam.codered.gamemap

import kotlinx.serialization.Serializable

@Serializable
data class BlockPoint(val x: Int, val y: Int, val z: Int)
