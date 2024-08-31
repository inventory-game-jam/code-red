package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.item.gun.GunType.Companion.buildGunType
import com.github.inventorygamejam.codered.item.gun.bullet.BulletTypes
import com.github.inventorygamejam.codered.util.BasicRegistry
import com.github.inventorygamejam.codered.util.mm
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
                    meta.displayName("<!i>Glock 18".mm)
                }
                item
            }
            bullet(BulletTypes.GLOCK18_BULLET)
            maxAmmo(30)
            cooldown(5)
        }
    )
    val AWP = register(
        "awp",
        buildGunType {
            item {
                val item = ItemStack.of(Material.GOLDEN_SHOVEL)
                item.editMeta { meta ->
                    meta.setCustomModelData(2)
                    meta.displayName("<!i>AWP".mm)
                }
                item
            }
            bullet(BulletTypes.GLOCK18_BULLET)
            maxAmmo(50)
            cooldown(2)
            zoomFactor(3)
        }
    )
}