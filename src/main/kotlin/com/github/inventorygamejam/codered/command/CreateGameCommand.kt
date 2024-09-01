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
import org.incendo.cloud.suggestion.SuggestionProvider.suggestingStrings

fun PaperCommandManager<CommandSourceStack>.registerCreateGameCommand() {
    buildAndRegister("creategame") {
        required("team1", quotedStringParser<CommandSourceStack>()) {
            suggestionProvider(
                suggestingStrings<CommandSourceStack>(
                    CodeRed.apiTeams.map(APITeam::name)
                )
            )
        }
        required("team2", quotedStringParser<CommandSourceStack>()) {
            suggestionProvider(
                suggestingStrings<CommandSourceStack>(
                    CodeRed.apiTeams.map(APITeam::name)
                )
            )
        }

        handler { ctx ->
            val apiTeams = runBlocking { InventoryGameJamAPI.getTeams() }
            val gameTeams = apiTeams.map(APITeam::toGameTeam)
            val team1 = gameTeams.find { gameTeam -> gameTeam.name == ctx.get<String>("team1") } ?: return@handler
            val team2 = gameTeams.find { gameTeam -> gameTeam.name == ctx.get<String>("team2") } ?: return@handler

            GameMatch(team1, team2)
        }
    }
}