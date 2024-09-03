package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.game.map.GameMap
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprites
import com.github.inventorygamejam.codered.matchmaking.Matchmaker
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.broadcast
import com.github.inventorygamejam.codered.message.Messages.spriteWithSubtitle
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.inventorygamejam.codered.team.PlayerRoleManager
import com.github.inventorygamejam.codered.util.registerEvents
import org.bukkit.GameMode
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
    val gameHandler = GameHandler(this)
    val players get() = defendingTeam.players + attackingTeam.players
    var currentCode = GameCode.GREEN

    fun isAttacker(player: Player) = player.uniqueId in attackingTeam.uuids
    fun isDefender(player: Player) = player.uniqueId in defendingTeam.uuids

    init {
        registerEvents(lootItemHandler, teamVaultHandler, gameHandler)

        map.placeSpawnPointBlocks()
        map.init()
        map.placeBuildings()

        teamVault = TeamVault(attackingTeam, map.attackerSpawns.first().clone().add(1.0, 2.0, 0.0))

        defendingTeam.players.forEachIndexed { i, player ->
            player.teleport(map.defenderSpawns[i].clone().add(0.0, 2.0, 0.0))

            PlayerRoleManager[player]?.equipItems(player)
        }
        attackingTeam.players.forEachIndexed { i, player ->
            player.teleport(map.attackerSpawns[i].clone().add(0.0, 2.0, 0.0))

            PlayerRoleManager[player]?.equipItems(player)
        }
    }

    fun codeYellow() {
        broadcast(Messages.ALL_ITEMS_COLLECTED)
        spriteWithSubtitle(RegisteredSprites.CODE_YELLOW, Messages.CODE_YELLOW)
        currentCode = GameCode.YELLOW
    }

    fun codeGreen() {
        broadcast(Messages.DEFENDERS_ESCAPED)
        broadcast(Messages.DEFENDERS_WIN)
        spriteWithSubtitle(RegisteredSprites.CODE_GREEN, Messages.CODE_GREEN)
        currentCode = GameCode.GREEN

        players.forEach { player ->
            player.gameMode = GameMode.SPECTATOR
        }

        Matchmaker.matchGenerator.endMatch(this)
    }

    fun codeRed() {
        broadcast(Messages.OBJECTIVE_DESTROYED)
        broadcast(Messages.ATTACKERS_WIN)
        spriteWithSubtitle(RegisteredSprites.CODE_RED, Messages.CODE_RED)
        currentCode = GameCode.RED

        players.forEach { player ->
            player.gameMode = GameMode.SPECTATOR
        }

        Matchmaker.matchGenerator.endMatch(this)
    }
}