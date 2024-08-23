package com.github.inventorygamejam.codered.gui.resourcepack

import net.radstevee.packed.core.pack.ResourcePack
import net.radstevee.packed.core.plugin.PackedPlugin

object ChevyFontsPackedPlugin : PackedPlugin {
    override fun beforeSave(pack: ResourcePack) {
        RegisteredFonts.REGISTERED_FONTS.filterIsInstance<ChevyFont>().forEach(ChevyFont::init)
    }
}