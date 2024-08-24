package com.github.inventorygamejam.codered.gui.resourcepack

import net.radstevee.packed.core.key.Key

object RegisteredSprites {
    val SCOPE = sprite(
        Key("codered", "gun/scope.png"),
        '\uE000',
        256.0,
        127.0,
    )

    fun init() {
        // Reflectively initialise all fields
        RegisteredSprites::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}