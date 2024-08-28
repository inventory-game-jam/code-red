package com.github.inventorygamejam.codered.gui

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.team.PlayerRoleManager
import com.github.inventorygamejam.codered.team.PlayerRoles
import com.github.inventorygamejam.codered.util.mm
import com.github.shynixn.mccoroutine.bukkit.launch
import com.noxcrew.interfaces.drawable.Drawable.Companion.drawable
import com.noxcrew.interfaces.element.StaticElement
import com.noxcrew.interfaces.interfaces.buildChestInterface
import org.bukkit.entity.Player

object PlayerRoleSelection {
    private val inventory = buildChestInterface {
        initialTitle = "Select your role.".mm
        rows = 1

        withTransform { pane, view ->
            PlayerRoles.forEachIndexed { i, role ->
                pane[0, if (i == 0) 1 else i * 2 + 1] = StaticElement(drawable(role.icon)) { ctx ->
                    PlayerRoleManager[view.player] = role
                    CodeRed.launch {
                        view.close()
                    }
                    view.player.sendRichMessage(Messages.ROLE_UPDATED)
                }
            }
        }
    }

    fun open(player: Player) {
        CodeRed.launch {
            inventory.open(player, null)
        }
    }
}