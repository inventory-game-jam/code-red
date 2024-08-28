package com.github.inventorygamejam.codered

import com.github.inventorygamejam.codered.command.registerCreateGameCommand
import com.github.inventorygamejam.codered.command.registerGunCommand
import com.github.inventorygamejam.codered.command.registerIGJCommand
import com.github.inventorygamejam.codered.command.registerRefreshAssetsCommand
import com.github.inventorygamejam.codered.command.registerSelectRoleCommand
import com.github.inventorygamejam.codered.game.map.GameMapConfig
import com.github.inventorygamejam.codered.gui.AmmoOverlay
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import com.github.inventorygamejam.codered.handler.GeneralPlayerHandler
import com.github.inventorygamejam.codered.item.gun.AmmoManager
import com.github.inventorygamejam.codered.item.gun.BulletHandler
import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.noxcrew.interfaces.InterfacesListeners
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.serialization.json.Json
import org.incendo.cloud.brigadier.BrigadierSetting
import org.incendo.cloud.execution.ExecutionCoordinator.simpleCoordinator
import org.incendo.cloud.paper.PaperCommandManager
import java.io.File

object CodeRed : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        commandManager = PaperCommandManager.builder().executionCoordinator(simpleCoordinator()).buildOnEnable(this)
        val brigSettings = commandManager.brigadierManager().settings()
        brigSettings.set(BrigadierSetting.FORCE_EXECUTABLE, true)

        apiKey = config.getString("apiKey") ?: error("No API key specified! DM rad to get one!")
        ghUsername = config.getString("ghUsername")
            ?: error("No github username specified! This is needed for cloning the asset repo!")
        ghPat =
            config.getString("ghPat") ?: error("No github PAT specified! This is needed for cloning the asset repo!")

        CodeRedPack.init()
        CodeRedPack.save()
        CodeRedPack.upload()

        BulletHandler

        AmmoManager
        AmmoOverlay

        InterfacesListeners.install(this)

        server.pluginManager.registerSuspendingEvents(GeneralPlayerHandler, this)

        gameMapConfig = Json.decodeFromString(File(assetPath, "configs/map_config.json").readText())

        commandManager.registerGunCommand()
        commandManager.registerCreateGameCommand()
        commandManager.registerRefreshAssetsCommand()
        commandManager.registerIGJCommand()
        commandManager.registerSelectRoleCommand()
    }

    override suspend fun onDisableAsync() {}

    lateinit var commandManager: PaperCommandManager<CommandSourceStack>
    lateinit var apiKey: String
    lateinit var ghUsername: String
    lateinit var ghPat: String
    lateinit var gameMapConfig: GameMapConfig
}
