package com.github.inventorygamejam.codered.gui.resourcepack

import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.pack
import kotlinx.serialization.json.Json
import net.radstevee.packed.core.font.Font
import java.io.File

data class ChevyFont(val metaFile: File, val ttfFile: File, override val registeredSprites: List<RegisteredSprite>) : UIFont {
    val metadata: FontMetadata = Json.decodeFromString(metaFile.readText())
    override val glyphWidths = metadata.glyphs.associate { it.char to it.width }.toMutableMap().apply {
        set(' ', values.first())
    }.filterKeys { char -> char.code > 0 }.toMap()

    fun init() {
        val ttfTargetFile = File(pack.outputDir, "assets/codered/font/${ttfFile.name}")
        runCatching { ttfFile.copyTo(ttfTargetFile) }
    }

    override lateinit var packedFont: Font
}
