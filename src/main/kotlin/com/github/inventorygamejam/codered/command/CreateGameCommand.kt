package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.game.GameMatch
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.parser.standard.StringParser.quotedStringParser
import org.incendo.cloud.suggestion.SuggestionProvider.suggestingStrings

fun PaperCommandManager<CommandSourceStack>.registerCreateGameCommand() {
    buildAndRegister("creategame") {
        required("team1", quotedStringParser()) {
            suggestionProvider(suggestingStrings(CodeRed.apiTeams.map { apiTeam -> "\"${apiTeam.name}\"" }))
        }
        required("team2", quotedStringParser()) {
            suggestionProvider(suggestingStrings(CodeRed.apiTeams.map { apiTeam -> "\"${apiTeam.name}\"" }))
        }

        handler { ctx ->
            val team1 = CodeRed.gameTeams.find { gameTeam -> gameTeam.name == ctx.get<String>("team1") } ?: return@handler
            val team2 = CodeRed.gameTeams.find { gameTeam -> gameTeam.name == ctx.get<String>("team2") } ?: return@handler

            GameMatch(team1, team2)
        }
    }
}