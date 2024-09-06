package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.team.GameTeam
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.type.Vault
import org.bukkit.inventory.ItemStack

class TeamVault(val team: GameTeam, val location: Location, val items: MutableList<ItemStack> = mutableListOf()) {
    val vault: Vault

    init {
        location.block.type = Material.VAULT
        vault = location.block.blockData as Vault

        vault
    }
}