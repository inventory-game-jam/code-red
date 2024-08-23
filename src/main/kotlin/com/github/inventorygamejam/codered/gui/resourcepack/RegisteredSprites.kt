package com.github.inventorygamejam.codered.gui.resourcepack

object RegisteredSprites {
    fun init() {
        // Reflectively initialise all fields
        RegisteredSprites::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}