package com.github.inventorygamejam.codered.item.gun.bullet

import com.github.inventorygamejam.codered.util.BasicRegistry

object BulletTypes : BasicRegistry<BulletType>() {
    val GLOCK18_BULLET = register(
        "glock18_bullet",
        BulletType(2.5, 20.0f, 50.0f)
    )
    val AWP_BULLET = register(
        "awp_bullet",
        BulletType(6.5, 50.0f, 100.0f)
    )
    val M16_BULLET = register(
        "m16_bullet",
        BulletType(3.5, 30.0f, 75.0f)
    )
}