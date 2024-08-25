package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.item.gun.Gun
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager

fun PaperCommandManager<CommandSourceStack>.registerGunCommand() {
    buildAndRegister("gun") {
        handler { ctx ->
            val player = ctx.sender().sender as Player
            player.inventory.addItem(Gun.item)
        }
    }
}