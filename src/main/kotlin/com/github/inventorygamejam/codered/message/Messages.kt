package com.github.inventorygamejam.codered.message

import com.github.inventorygamejam.codered.util.mm
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.title.Title.title
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.getServer

object Messages {
    fun broadcast(message: String) = broadcast(message.mm)

    fun debug(message: String) = broadcast("<dark_gray>Debug |</dark_gray> $message".mm, "minecraft.op")

    fun title(message: String) = getServer().showTitle(title(message.mm, empty()))

    const val CODE_RED = "<dark_gray>Code <red>RED"
    const val CODE_YELLOW = "<dark_gray>Code <yellow>YELLOW"
    const val CODE_GREEN = "<dark_gray>Code <green>GREEN"
    const val OBJECTIVE_DESTROYED = "<red>The objective has been destroyed."
    const val ATTACKERS_WIN = "<gray>The <red>attackers</red> win."
    const val DEFENDERS_WIN = "<gray>The <blue>defenders</blue> win."
}
