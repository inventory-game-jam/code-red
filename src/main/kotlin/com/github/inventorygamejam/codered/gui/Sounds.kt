package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.util.BasicRegistry
import net.kyori.adventure.key.Key.key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.PLAYER
import net.kyori.adventure.sound.Sound.sound

object Sounds : BasicRegistry<Sound>() {
    val GUN_FIRE = register("gun.fire", sound(key("codered", "gun.fire"), PLAYER, 0.2f, 1f))
}