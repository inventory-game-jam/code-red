package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.gui.PlayerRoleSelection
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager

fun PaperCommandManager<CommandSourceStack>.registerSelectRoleCommand() {
    buildAndRegister("selectrole") {
        handler { ctx ->
            PlayerRoleSelection.open(ctx.sender().sender as? Player ?: return@handler)
        }
    }
}