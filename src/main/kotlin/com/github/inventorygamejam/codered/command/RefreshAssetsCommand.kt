package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.CodeRed.gameMapConfig
import com.github.inventorygamejam.codered.CodeRed.ghPat
import com.github.inventorygamejam.codered.CodeRed.ghUsername
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.sendPack
import com.github.syari.kgit.KGit.Companion.cloneRepository
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import java.io.File

fun PaperCommandManager<CommandSourceStack>.registerRefreshAssetsCommand() {
    buildAndRegister("refreshassets") {
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

            gameMapConfig = Json.decodeFromString(File(assetPath, "configs/map_config.json").readText())

            CodeRed.logger.info("Initialising schematic...")
            val startTime = System.currentTimeMillis()
            val mapSchematicFile = File(assetPath, "schematics/map.schem")
            val clipFormat = ClipboardFormats.findByFile(mapSchematicFile) ?: error("failed to find clipboard format")
            val reader = clipFormat.getReader(mapSchematicFile.inputStream())
            CodeRed.mapSchematic = reader.use(ClipboardReader::read)
            CodeRed.logger.info("Initialising schematic took ${System.currentTimeMillis() - startTime}ms")

            Bukkit.getOnlinePlayers().forEach { player ->
                player.clearResourcePacks()
                player.sendPack()
            }
        }
    }
}