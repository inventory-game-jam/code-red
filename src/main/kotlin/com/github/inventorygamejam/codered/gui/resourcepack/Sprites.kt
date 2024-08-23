package com.github.inventorygamejam.codered.gui.resourcepack

import com.github.inventorygamejam.codered.util.buildText
import net.radstevee.packed.core.key.Key

data class Sprite(val name: String, val key: Key, val height: Double, val baseAscent: Double, val width: Int, val preSpace: Int = 0)

fun sprite(name: String, key: Key, char: Char, height: Double, baseAscent: Double, width: Int, showShadow: Boolean = false, preSpace: Int = 0) =
    RegisteredSprite(char, Sprite(name, key, height, baseAscent, width, preSpace), showShadow)

data class RegisteredSprite(val char: Char, val sprite: Sprite, var showShadow: Boolean) {
    lateinit var font: Key

    fun component() = buildText {
        append(char.toString())
        font(font.toString())
    }
}
