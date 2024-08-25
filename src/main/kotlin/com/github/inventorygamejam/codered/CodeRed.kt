package com.github.inventorygamejam.codered

import com.github.inventorygamejam.codered.gamemap.GameMap
import com.github.inventorygamejam.codered.gamemap.GameMapConfig
import com.github.inventorygamejam.codered.gui.AmmoOverlay
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.sendPack
import com.github.inventorygamejam.codered.handler.GeneralPlayerHandler
import com.github.inventorygamejam.codered.item.gun.AmmoManager
import com.github.inventorygamejam.codered.item.gun.BulletHandler
import com.github.inventorygamejam.codered.item.gun.Gun
import com.github.inventorygamejam.codered.matchmaking.Matchmaker
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.github.syari.kgit.KGit.Companion.cloneRepository
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.incendo.cloud.brigadier.BrigadierSetting
import org.incendo.cloud.execution.ExecutionCoordinator.simpleCoordinator
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import java.io.File
import java.util.UUID

object CodeRed : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        commandManager = PaperCommandManager.builder().executionCoordinator(simpleCoordinator()).buildOnEnable(this)
        val brigSettings = commandManager.brigadierManager().settings()
        brigSettings.set(BrigadierSetting.FORCE_EXECUTABLE, true)

        apiKey = config.getString("apiKey") ?: error("No API key specified! DM rad to get one!")
        ghUsername = config.getString("ghUsername") ?: error("No github username specified! This is needed for cloning the asset repo!")
        ghPat = config.getString("ghPat") ?: error("No github PAT specified! This is needed for cloning the asset repo!")

        CodeRedPack.init()
        CodeRedPack.save()
        CodeRedPack.upload()

        BulletHandler

        AmmoManager
        AmmoOverlay

        server.pluginManager.registerSuspendingEvents(GeneralPlayerHandler, this)

        commandManager.buildAndRegister("gun") {
            handler { ctx ->
                val player = ctx.sender().sender as Player
                player.inventory.addItem(Gun.item)
            }
        }

        commandManager.buildAndRegister("creategame") {
            handler { ctx ->
                val player = ctx.sender().sender as Player
                val config = Json.decodeFromString<GameMapConfig>(File(assetPath, "configs/map_config.json").readText())
                val map = GameMap(config)
                map.init()
                map.placeBuildings()
                map.placeSpawnPointBlocks()
                val location = map.attackerSpawns.first().add(0.0, 2.0, 0.0)
                player.teleport(location)
            }
        }

        commandManager.buildAndRegister("refreshassets") {
            permission("minecraft.op")
            handler { _ ->
                cloneRepository {
                    setURI("https://github.com/inventory-game-jam/code-red-assets.git")
                    assetPath.deleteRecursively()
                    setDirectory(assetPath)

                    setCredentialsProvider(UsernamePasswordCredentialsProvider(ghUsername, ghPat))
                }

                CodeRedPack.init()
                CodeRedPack.save()
                runBlocking { CodeRedPack.upload() }

                Bukkit.getOnlinePlayers().forEach { player ->
                    player.clearResourcePacks()
                    player.sendPack()
                }
            }
        }
    }

    override suspend fun onDisableAsync() {}

    lateinit var commandManager: PaperCommandManager<CommandSourceStack>
    lateinit var apiKey: String
    lateinit var ghUsername: String
    lateinit var ghPat: String
}
