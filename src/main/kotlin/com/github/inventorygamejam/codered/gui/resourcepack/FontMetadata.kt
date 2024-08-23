package com.github.inventorygamejam.codered.gui.resourcepack

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FontMetadata(
    val size: Int,
    @SerialName("line_gap") val lineGap: Int,
    @SerialName("space_w") val spaceWidth: Int,
    val glyphs: List<Glyph>,
    val kerning: List<Kerning>? = null
)

@Serializable
data class Glyph(
    @SerialName("chr") val char: Char,
    val x: Int,
    val y: Int,
    @SerialName("w") val width: Int,
    @SerialName("h") val height: Int,
    @SerialName("off_x") val offsetX: Int,
    @SerialName("off_y") val offsetY: Int,
    @SerialName("adv") val advance: Int
)

@Serializable
data class Kerning(
    val left: Char,
    val right: Char,
    @SerialName("kern") val kerning: Int
)
