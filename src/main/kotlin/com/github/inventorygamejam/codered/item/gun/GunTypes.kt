package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.item.gun.GunType.Companion.buildGunType
import com.github.inventorygamejam.codered.item.gun.bullet.BulletTypes
import com.github.inventorygamejam.codered.util.BasicRegistry
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GunTypes : BasicRegistry<GunType>() {
    val GLOCK18 = register(
        "glock18",
        buildGunType {
            item {
                val item = ItemStack.of(Material.GOLDEN_SHOVEL)
                item.editMeta { meta ->
                    meta.setCustomModelData(1)
                }
                item
            }
            bullet(BulletTypes.GLOCK18_BULLET)
        }
    )
}