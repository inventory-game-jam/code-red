package com.github.inventorygamejam.codered.team

import org.bukkit.entity.Player

object PlayerRoleManager {
    private val roles = mutableMapOf<Player, PlayerRole>()

    operator fun set(player: Player, role: PlayerRole) {
        roles[player] = role
    }

    operator fun get(player: Player) = roles[player]
}