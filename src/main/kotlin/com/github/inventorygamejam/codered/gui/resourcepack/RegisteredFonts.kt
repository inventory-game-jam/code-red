package com.github.inventorygamejam.codered.gui.resourcepack

object RegisteredFonts {
    val REGISTERED_FONTS = mutableListOf<UIFont>()
    val ICONS = registerSpriteOnlyFont("icons", RegisteredSprites.SCOPE)
    val AMMO_OVERLAY = registerChevyFont("beaver_upper", 56.0, 1.5)

    fun init() {
        // Initialise all properties reflectively
        RegisteredFonts::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}