package com.github.inventorygamejam.codered.team

enum class TeamSide {
    ATTACK,
    DEFEND;

    fun opposite() = if (this == ATTACK) DEFEND else ATTACK
}