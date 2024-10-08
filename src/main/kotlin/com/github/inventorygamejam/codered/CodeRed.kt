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
import com.github.inventorygamejam.codered.handler.MainObjectiveBuildHandler
import com.github.inventorygamejam.codered.item.gun.GunHandler
import com.github.inventorygamejam.codered.item.gun.bullet.BulletHandler
import com.github.inventorygamejam.codered.team.GameTeam
import com.github.inventorygamejam.codered.util.APITeam
import com.github.inventorygamejam.codered.util.InventoryGameJamAPI
import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.noxcrew.interfaces.InterfacesListeners
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader
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

        fetchTeams()

        CodeRedPack.init()
        CodeRedPack.save()
        CodeRedPack.upload()

        BulletHandler
        GeneralPlayerHandler
        MainObjectiveBuildHandler
        GunHandler
        AmmoOverlay

        InterfacesListeners.install(this)

        gameMapConfig = Json.decodeFromString(File(assetPath, "configs/map_config.json").readText())

        logger.info("Initialising schematic...")
        val startTime = System.currentTimeMillis()
        val mapSchematicFile = File(assetPath, "schematics/map.schem")
        val clipFormat = ClipboardFormats.findByFile(mapSchematicFile) ?: error("failed to find clipboard format")
        val reader = clipFormat.getReader(mapSchematicFile.inputStream())
        mapSchematic = reader.use(ClipboardReader::read)
        logger.info("Initialising schematic took ${System.currentTimeMillis() - startTime}ms")

        commandManager.registerGunCommand()
        commandManager.registerCreateGameCommand()
        commandManager.registerRefreshAssetsCommand()
        commandManager.registerIGJCommand()
        commandManager.registerSelectRoleCommand()
    }

    override suspend fun onDisableAsync() {}

    suspend fun fetchTeams() {
        logger.info("Fetching teams...")
        val startTime = System.currentTimeMillis()
        apiTeams = InventoryGameJamAPI.getTeams()
        gameTeams = apiTeams.map { apiTeam -> apiTeam.toGameTeam() }
        logger.info("Fetching teams took ${System.currentTimeMillis() - startTime}ms")
    }

    lateinit var commandManager: PaperCommandManager<CommandSourceStack>
    lateinit var apiTeams: List<APITeam>
    lateinit var gameTeams: List<GameTeam>
    lateinit var apiKey: String
    lateinit var ghUsername: String
    lateinit var ghPat: String
    lateinit var gameMapConfig: GameMapConfig
    lateinit var mapSchematic: Clipboard
}
