package com.github.inventorygamejam.codered.gui.resourcepack

object RegisteredFonts {
    val REGISTERED_FONTS = mutableListOf<UIFont>()
    val ICONS = registerSpriteOnlyFont(
        "icons", RegisteredSprites.SCOPE, RegisteredSprites.CODE_RED, RegisteredSprites.CODE_YELLOW,
        RegisteredSprites.CODE_GREEN
    )
    val AMMO_OVERLAY = registerChevyFont("beaver_mono", 57.0, 1.75)
    val AMMO_OVERLAY_SUB = registerChevyFont("beaver_mono", 45.0, 1.0)

    fun init() {
        // Initialise all properties reflectively
        RegisteredFonts::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}