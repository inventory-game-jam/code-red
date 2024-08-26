package com.github.inventorygamejam.codered.team

import org.bukkit.Bukkit
import java.util.UUID

data class GameTeam(val name: String, val uuids: MutableList<UUID>) {
    val players get() = uuids.mapNotNull(Bukkit::getPlayer)
    // please add some methods to handle minecraft teams
}