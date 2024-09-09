@file:OptIn(DelicateCoroutinesApi::class)

package com.github.inventorygamejam.codered.game

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.game.map.GameMap
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredFonts
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprites
import com.github.inventorygamejam.codered.handler.MainObjectiveBuildHandler
import com.github.inventorygamejam.codered.handler.MainObjectiveBuildHandler.MAIN_OBJECTIVE_TAG_KEY
import com.github.inventorygamejam.codered.matchmaking.Matchmaker
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.broadcast
import com.github.inventorygamejam.codered.message.Messages.debug
import com.github.inventorygamejam.codered.message.Messages.spriteWithSubtitle
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.inventorygamejam.codered.team.PlayerRoleManager
import com.github.inventorygamejam.codered.team.PlayerRoles
import com.github.inventorygamejam.codered.util.InventoryGameJamAPI
import com.github.inventorygamejam.codered.util.buildText
import com.github.inventorygamejam.codered.util.registerEvents
import com.github.inventorygamejam.codered.util.runTask
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Marker
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class GameMatch(val defendingTeam: GameTeam, val attackingTeam: GameTeam) {
    val map = GameMap()
    val teamVault: TeamVault
    val lootItems = mutableListOf(
        LootItem(
            map.attackerSpawns.last().clone().add(1.0, 3.0, 3.0),
            ItemStack.of(Material.DIAMOND_SWORD)
        ),
        LootItem(
            map.attackerSpawns.last().clone().add(1.0, 3.0, 0.0),
            ItemStack.of(Material.NETHERITE_SWORD)
        )
    )
    val lootItemHandler = LootItemHandler(this)
    val teamVaultHandler = TeamVaultHandler(this)
    val gameHandler = GameHandler(this)
    val players get() = defendingTeam.players + attackingTeam.players
    var currentCode: GameCode? = null
    var mainObjectiveLocations = listOf<Location>()

    fun isAttacker(player: Player) = player.uniqueId in attackingTeam.uuids
    fun isDefender(player: Player) = player.uniqueId in defendingTeam.uuids
    fun team(player: Player) = if (isAttacker(player)) attackingTeam else defendingTeam

    init {
        registerEvents(lootItemHandler, teamVaultHandler, gameHandler)

        map.init()
        map.removeSpawnPointBlocks()

        teamVault = TeamVault(defendingTeam, map.vaultLocation)

        players.forEach { player ->
            player.gameMode = GameMode.ADVENTURE
            player.inventory.clear()
            player.health = 20.0
            player.saturation = 10f
            player.foodLevel = 20
        }

        defendingTeam.players.forEachIndexed { i, player ->
            val spawn = map.defenderSpawns[i]
            player.teleport(spawn)
            player.setRespawnLocation(spawn, true)

            PlayerRoleManager[player]?.equipItems(player)
        }

        attackingTeam.players.forEachIndexed { i, player ->
            val spawn = map.attackerSpawns[i]
            player.teleport(spawn)
            player.setRespawnLocation(spawn, true)

            PlayerRoleManager[player]?.equipItems(player)
            player.inventory.setItem(8, PlayerRoles.Items.PICKAXE)
            player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, -1, 1, false, false, false))
        }

        val markers = map.world.getEntitiesByClasses(Marker::class.java)
        mainObjectiveLocations = markers
            .filter { marker -> MAIN_OBJECTIVE_TAG_KEY in marker.scoreboardTags }
            .map(Entity::getLocation)
            .map(Location::toBlockLocation)

        Bukkit.getScheduler().scheduleSyncRepeatingTask(CodeRed, {
            if (currentCode in listOf(GameCode.RED, GameCode.GREEN)) return@scheduleSyncRepeatingTask
            broadcast(Messages.TIPS.random())
        }, 20 * 45, 20 * 60)
    }

    fun codeYellow() {
        broadcast(Messages.ALL_ITEMS_COLLECTED)
        spriteWithSubtitle(RegisteredSprites.CODE_YELLOW, Messages.CODE_YELLOW)
        currentCode = GameCode.YELLOW

        runTask(60) {
            if (currentCode == GameCode.YELLOW) codeGreen()
        }
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

        defendingTeam.uuids.forEach { uuid ->
            GlobalScope.launch {
                InventoryGameJamAPI.addPlayerScore(uuid, 100)
            }
        }
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

        attackingTeam.uuids.forEach { uuid ->
            GlobalScope.launch {
                InventoryGameJamAPI.addPlayerScore(uuid, 100)
            }
        }
    }

    fun handleKill(player: Player, killer: Player?) {
        var actionbarMessage = "[you died]"
        player.gameMode = GameMode.SPECTATOR
        runTask(10 * 20) {
            respawnPlayer(player)
        }

        if (killer != null) {
            GlobalScope.launch {
                InventoryGameJamAPI.addPlayerScore(killer.uniqueId, 100)
            }

            actionbarMessage = "you were killed by [${killer.name}]"

            runTask(1) {
                player.teleport(killer)
            }

            runTask(15) {
                player.spectatorTarget = killer
            }

            runTask(4 * 20) {
                player.spectatorTarget = null
            }
        } else {
            runTask(1) {
                val index = team(player).uuids.indexOf(player.uniqueId)
                val spawn = (if (isDefender(player)) map.defenderSpawns else map.attackerSpawns)[index]
                player.teleport(spawn)
            }
        }

        player.sendActionBar(buildText {
            append(actionbarMessage)
            gray()
            font(RegisteredFonts.BEAVER)
        })
    }

    fun respawnPlayer(player: Player) {
        val index = team(player).uuids.indexOf(player.uniqueId)
        val spawn = (if (isDefender(player)) map.defenderSpawns else map.attackerSpawns)[index]
        player.teleport(spawn.clone().add(0.0, 2.0, 0.0))
        player.gameMode = GameMode.ADVENTURE
        player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, -1, 2, false, false, false))
    }
}