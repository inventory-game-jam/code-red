package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.team.PlayerRoleManager
import com.github.inventorygamejam.codered.team.PlayerRoles
import com.github.inventorygamejam.codered.util.mm
import com.github.inventorygamejam.codered.util.withName
import com.github.inventorygamejam.codered.util.withoutTooltip
import com.github.shynixn.mccoroutine.bukkit.launch
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildChestInterface
import com.noxcrew.interfaces.utilities.forEachInGrid
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PlayerRoleSelection {
    private val inventory = buildChestInterface {
        initialTitle = "Select your role.".mm
        rows = 3

        withTransform { pane, view ->
            PlayerRoles.forEachIndexed { i, role ->
                pane[1, if (i == 0) 1 else i * 2 + 1] = StaticElement(drawable(role.icon)) { ctx ->
                    PlayerRoleManager[view.player] = role
                    CodeRed.launch {
                        view.close()
                    }
                    view.player.sendRichMessage(Messages.ROLE_UPDATED)
                }
            }
        }

        withTransform { pane, _ ->
            forEachInGrid(3, 9) { row, column ->
                if (pane.has(row, column)) return@forEachInGrid
                val item = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
                    .withName("")
                    .withoutTooltip()

                pane[row, column] = StaticElement(drawable(item))
            }
        }
    }

    fun open(player: Player) {
        CodeRed.launch {
            inventory.open(player, null)
        }
    }
}