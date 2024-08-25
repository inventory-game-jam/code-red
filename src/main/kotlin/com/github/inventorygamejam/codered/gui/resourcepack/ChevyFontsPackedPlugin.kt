package com.github.inventorygamejam.codered.gui.resourcepack

import net.radstevee.packed.core.pack.ResourcePack
import net.radstevee.packed.core.plugin.PackedPlugin
import java.io.File

object ChevyFontsPackedPlugin : PackedPlugin {
    override fun beforeSave(pack: ResourcePack) {
        RegisteredFonts.REGISTERED_FONTS.filterIsInstance<ChevyFont>().forEach(ChevyFont::init)
        File(pack.outputDir, "fonts").deleteRecursively()
        File(pack.outputDir, ".git").deleteRecursively()
        File(pack.outputDir, "schematics").deleteRecursively()
        File(pack.outputDir, "configs").deleteRecursively()
    }
}