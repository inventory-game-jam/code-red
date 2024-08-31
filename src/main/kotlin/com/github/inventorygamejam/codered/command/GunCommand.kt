package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.item.gun.GunTypes
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.parser.standard.StringParser
import org.incendo.cloud.suggestion.SuggestionProvider

fun PaperCommandManager<CommandSourceStack>.registerGunCommand() {
    buildAndRegister("gun") {
        required("type", StringParser.stringParser<CommandSourceStack>()) {
            suggestionProvider(
                SuggestionProvider.suggestingStrings<CommandSourceStack>(GunTypes.keys)
            )
        }
        handler { ctx ->
            val player = ctx.sender().sender as Player
            val type = GunTypes[ctx.get<String>("type")] ?: return@handler

            player.inventory.addItem(type.createGun().item)
        }
    }
}