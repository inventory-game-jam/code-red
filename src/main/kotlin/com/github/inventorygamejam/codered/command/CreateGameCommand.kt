package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.gamemap.GameMap
import com.github.inventorygamejam.codered.gamemap.GameMapConfig
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.assetPath
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import java.io.File

fun PaperCommandManager<CommandSourceStack>.registerCreateGameCommand() {
    buildAndRegister("creategame") {
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
}