package com.github.inventorygamejam.codered.gui.resourcepack

import net.radstevee.packed.core.key.Key

object RegisteredSprites {
    val SCOPE = registerSprite(
        Key("codered", "gun/scope.png"),
        '\uE000',
        256.0,
        125.0,
    )
    val CODE_RED = registerSprite(
        Key("codered", "gui/code_red.png"),
        '\uE001',
        16.0,
        15.0
    )
    val CODE_YELLOW = registerSprite(
        Key("codered", "gui/code_yellow.png"),
        '\uE002',
        16.0,
        15.0
    )
    val CODE_GREEN = registerSprite(
        Key("codered", "gui/code_green.png"),
        '\uE003',
        16.0,
        15.0
    )

    fun init() {
        // Reflectively initialise all fields
        RegisteredSprites::class.java.declaredFields.forEach { field -> field.get(null) }
    }
}