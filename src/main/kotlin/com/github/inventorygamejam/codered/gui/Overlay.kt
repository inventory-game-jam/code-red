package com.github.inventorygamejam.codered.gui

import org.bukkit.entity.Player

interface Overlay {
    fun render(player: Player)
}