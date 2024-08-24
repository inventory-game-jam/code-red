package com.github.inventorygamejam.codered.team

import com.github.inventorygamejam.codered.util.BasicRegistry

object PlayerClasses : BasicRegistry<PlayerClass>() {
    val SNIPER = register("sniper", PlayerClass)
    val ASSAULTER = register("assaulter", PlayerClass)
    val MEDIC = register("medic", PlayerClass)
    val SUPPORTER = register("supporter", PlayerClass)
}