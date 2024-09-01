package com.github.inventorygamejam.codered.gui.resourcepack

import com.github.inventorygamejam.codered.util.buildText
import net.radstevee.packed.core.key.Key

data class Sprite(val key: Key, val height: Double, val ascent: Double, val width: Int, val preSpace: Int = 0)

fun sprite(key: Key, char: Char, height: Double, ascent: Double, showShadow: Boolean = false, preSpace: Int = 0) =
    RegisteredSprite(char, Sprite(key, height, ascent, preSpace), showShadow)

data class RegisteredSprite(val char: Char, val sprite: Sprite, var showShadow: Boolean) {
    lateinit var font: Key

    fun component() = buildText {
        append(char.toString())
        font(font.toString())

        if (!showShadow) removeTextShadow()
    }
}
