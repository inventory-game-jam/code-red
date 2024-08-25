package com.github.inventorygamejam.codered.team

import com.github.inventorygamejam.codered.util.BasicRegistry

object PlayerRoles : BasicRegistry<PlayerRole>() {
    val SNIPER = register("sniper", PlayerRole)
    val ASSAULTER = register("assaulter", PlayerRole)
    val MEDIC = register("medic", PlayerRole)
    val SUPPORTER = register("supporter", PlayerRole)
}