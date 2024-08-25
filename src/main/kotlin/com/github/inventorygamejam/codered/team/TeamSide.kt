package com.github.inventorygamejam.codered.team

enum class TeamSide {
    ATTACK,
    DEFEND;

    fun opposite(): TeamSide {
        return if(this == ATTACK) DEFEND else ATTACK
    }
}