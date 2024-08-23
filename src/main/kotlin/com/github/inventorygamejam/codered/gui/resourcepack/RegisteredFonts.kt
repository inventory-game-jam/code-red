package com.github.inventorygamejam.codered.gui.resourcepack

object RegisteredFonts {
    val REGISTERED_FONTS = mutableListOf<UIFont>()
    val QUIP = registerChevyFont("quip")
    val BEAVER = registerChevyFont("beaver_upper")

    fun init() {
        // Initialise all properties reflectively
        RegisteredFonts::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}