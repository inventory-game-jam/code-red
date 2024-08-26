package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.game.map.GameMap
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprites
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.broadcast
import com.github.inventorygamejam.codered.message.Messages.debug
import com.github.inventorygamejam.codered.message.Messages.spriteWithSubtitle
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.inventorygamejam.codered.util.registerEvents
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.Audience.audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GameMatch(val defendingTeam: GameTeam, val attackingTeam: GameTeam) {
    val map = GameMap()
    val teamVault: TeamVault
    val lootItems = mutableListOf<LootItem>(
        LootItem(
            map.attackerSpawns.last().clone().add(1.0, 3.0, 3.0), ItemStack.of(
                Material.DIAMOND_SWORD
            )
        ),
        LootItem(
            map.attackerSpawns.last().clone().add(1.0, 3.0, 0.0), ItemStack.of(
                Material.NETHERITE_SWORD
            )
        )
    )
    val lootItemHandler = LootItemHandler(this)
    val teamVaultHandler = TeamVaultHandler(this)
    val players get() = defendingTeam.players + attackingTeam.players

    fun isAttacker(player: Player) = player.uniqueId in attackingTeam.uuids
    fun isDefender(player: Player) = player.uniqueId in defendingTeam.uuids

    init {
        registerEvents(lootItemHandler, teamVaultHandler)

        map.placeSpawnPointBlocks()
        map.init()
        map.placeBuildings()

        teamVault = TeamVault(attackingTeam, map.attackerSpawns.first().clone().add(1.0, 2.0, 0.0))

        defendingTeam.players.forEachIndexed { i, player ->
            player.teleport(map.defenderSpawns[i].clone().add(0.0, 2.0, 0.0))
        }
        attackingTeam.players.forEachIndexed { i, player ->
            player.teleport(map.attackerSpawns[i].clone().add(0.0, 2.0, 0.0))
        }
    }

    fun codeYellow() {
        broadcast(Messages.ALL_ITEMS_COLLECTED)
        spriteWithSubtitle(RegisteredSprites.CODE_YELLOW, Messages.CODE_YELLOW)
    }
}