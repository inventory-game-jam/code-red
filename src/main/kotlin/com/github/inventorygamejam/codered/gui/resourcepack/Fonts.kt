package com.github.inventorygamejam.codered.gui.resourcepack

import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.pack
import net.radstevee.packed.core.font.Font
import net.radstevee.packed.core.key.Key
import java.io.File

interface UIFont {
    val packedFont: Font
    val glyphWidths: Map<Char, Int>
    val registeredSprites: List<RegisteredSprite>
}

fun registerChevyFont(font: String, verticalShift: Double = 0.0, scaling: Double = 1.0, prefix: String = font, vararg sprites: RegisteredSprite): ChevyFont {
    val chevyFont = ChevyFont(
        metaFile = File(assetPath, "fonts/packed/${font.removeSuffix("_bold")}/$font.json"),
        ttfFile = File(assetPath, "fonts/ttf/$font.ttf"),
        registeredSprites = sprites.toList()
    )
    chevyFont.packedFont = pack.addFont {
        key = Key("codered", if (verticalShift != 0.0) prefix + "_offset_$verticalShift" else prefix)

        ttf {
            key = Key("codered", "$font.ttf")
            shift = listOf(0.0, verticalShift)
            size = chevyFont.metadata.size * scaling
            oversample = size + scaling
        }

        sprites.forEach { sprite ->
            sprite.font = this.key
            bitmap {
                key = sprite.sprite.key
                height = sprite.sprite.height * scaling
                chars = listOf(sprite.char.toString())
                ascent = (sprite.sprite.baseAscent + verticalShift) * scaling
            }
        }
    }

    RegisteredFonts.REGISTERED_FONTS.add(chevyFont)

    return chevyFont
}
