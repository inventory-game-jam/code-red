package com.github.inventorygamejam.codered

import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack
import com.github.inventorygamejam.codered.handler.GeneralPlayerHandler
import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.incendo.cloud.brigadier.BrigadierSetting
import org.incendo.cloud.execution.ExecutionCoordinator.asyncCoordinator
import org.incendo.cloud.paper.PaperCommandManager

object CodeRed : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        commandManager = PaperCommandManager.builder().executionCoordinator(asyncCoordinator()).buildOnEnable(this)
        val brigSettings = commandManager.brigadierManager().settings()
        brigSettings.set(BrigadierSetting.FORCE_EXECUTABLE, true)

        apiKey = config.getString("apiKey") ?: error("No API key specified! DM rad to get one!")
        ghUsername = config.getString("ghUsername") ?: error("No github username specified! This is needed for cloning the asset repo!")
        ghPat = config.getString("ghPat") ?: error("No github PAT specified! This is needed for cloning the asset repo!")

        CodeRedPack.init()
        CodeRedPack.save()
        CodeRedPack.upload()

        server.pluginManager.registerSuspendingEvents(GeneralPlayerHandler, this)
    }

    override suspend fun onDisableAsync() {}

    lateinit var commandManager: PaperCommandManager<CommandSourceStack>
    lateinit var apiKey: String
    lateinit var ghUsername: String
    lateinit var ghPat: String
}
