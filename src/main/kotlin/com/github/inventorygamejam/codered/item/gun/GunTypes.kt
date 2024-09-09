package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.item.gun.GunType.Companion.buildGunType
import com.github.inventorygamejam.codered.item.gun.bullet.BulletTypes
import com.github.inventorygamejam.codered.util.BasicRegistry
import com.github.inventorygamejam.codered.util.editItemMeta
import com.github.inventorygamejam.codered.util.mm
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GunTypes : BasicRegistry<GunType>() {
    val GLOCK18 = register(
        "glock18",
        buildGunType {
            item {
                val item = ItemStack.of(Material.GOLDEN_SHOVEL)
                item.editItemMeta {
                    setCustomModelData(1)
                    displayName("<!i>Glock 18".mm)
                    isUnbreakable = true
                }
            }
            bullet(BulletTypes.GLOCK18_BULLET)
            maxAmmo(30)
            wholeMagazineReload(80)
            insideMagazineReload(5)

            verticalRecoil(1f)
            horizontalRecoil(0.5f)
        }
    )
    val AWP = register(
        "awp",
        buildGunType {
            item {
                val item = ItemStack.of(Material.GOLDEN_SHOVEL)
                item.editItemMeta {
                    setCustomModelData(2)
                    displayName("<!i>AWP".mm)
                    isUnbreakable = true
                }
            }
            bullet(BulletTypes.AWP_BULLET)
            maxAmmo(10)
            wholeMagazineReload(150)
            insideMagazineReload(15)
            zoomFactor(15)

            verticalRecoil(15f)
            horizontalRecoil(4f)
        }
    )
    val M16 = register(
        "m16",
        buildGunType {
            item {
                val item = ItemStack.of(Material.GOLDEN_SHOVEL)
                item.editItemMeta {
                    setCustomModelData(3)
                    displayName("<!i>M16 Assault Rifle".mm)
                    isUnbreakable = true
                }
            }
            bullet(BulletTypes.M16_BULLET)
            maxAmmo(30)
            wholeMagazineReload(100)
            insideMagazineReload(3)

            verticalRecoil(3f)
            horizontalRecoil(1f)
        }
    )
}