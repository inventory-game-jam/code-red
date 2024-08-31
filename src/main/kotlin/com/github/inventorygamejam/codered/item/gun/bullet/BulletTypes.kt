package com.github.inventorygamejam.codered.item.gun.bullet

import com.github.inventorygamejam.codered.util.BasicRegistry

object BulletTypes : BasicRegistry<BulletType>() {
    val GLOCK18_BULLET = register(
        "glock18_bullet",
        BulletType(3.0, 20.0f, 50.0f)
    )
}