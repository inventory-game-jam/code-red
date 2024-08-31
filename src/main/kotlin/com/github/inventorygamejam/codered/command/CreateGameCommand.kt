package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.game.GameMatch
import com.github.inventorygamejam.codered.util.APITeam
import com.github.inventorygamejam.codered.util.InventoryGameJamAPI
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.coroutines.runBlocking
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.parser.standard.StringParser.quotedStringParser
import org.incendo.cloud.suggestion.SuggestionProvider

fun PaperCommandManager<CommandSourceStack>.registerCreateGameCommand() {
    buildAndRegister("creategame") {
        required("team1", quotedStringParser<CommandSourceStack>()) {
            suggestionProvider(
                SuggestionProvider.suggestingStrings<CommandSourceStack>(
                    CodeRed.apiTeams.map { it.name }
                )
            )
        }
        required("team2", quotedStringParser<CommandSourceStack>()) {
            suggestionProvider(
                SuggestionProvider.suggestingStrings<CommandSourceStack>(
                    CodeRed.apiTeams.map { it.name }
                )
            )
        }

        handler { ctx ->
            val apiTeams = runBlocking { InventoryGameJamAPI.getTeams() }
            val gameTeams = apiTeams.map(APITeam::toGameTeam)
            val team1 = gameTeams.find { it.name == ctx.get<String>("team1") }!!
            val team2 = gameTeams.find { it.name == ctx.get<String>("team2") }!!

            GameMatch(team1, team2)
            //gameTeams.forEach(Matchmaker::addTeam)
            /* val map = GameMap(config)
            map.init()
            map.placeBuildings()
            map.placeSpawnPointBlocks()
            val location = map.attackerSpawns.first().add(0.0, 2.0, 0.0)
            player.teleport(location) */
        }
    }
}