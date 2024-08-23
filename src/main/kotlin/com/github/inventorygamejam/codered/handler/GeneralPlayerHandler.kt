package com.github.inventorygamejam.codered.handler

import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.sendPack
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object GeneralPlayerHandler : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        player.sendPack()
    }
}