package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.gui.Sounds
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprite
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprites
import com.github.inventorygamejam.codered.item.gun.bullet.BulletType
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.title.Title.Times.times
import net.kyori.adventure.title.Title.title
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration.ofDays
import java.time.Duration.ofMillis

class GunType private constructor(
    private val item: () -> ItemStack,
    val bullet: BulletType,
    val cooldown: Int,
    val maxAmmo: Int,
    val zoomFactor: Int = 2,
    val scopeSprite: RegisteredSprite? = RegisteredSprites.SCOPE,
    val shootSound: Sound = Sounds.GUN_FIRE,
) {
    fun showScope(show: Boolean, player: Player) {
        if (scopeSprite == null) return
        if (show) {
            player.showTitle(
                title(
                    scopeSprite.component(),
                    empty(),
                    times(ofMillis(100), ofDays(10), ofMillis(100)),
                )
            )
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOWNESS,
                    -1,
                    zoomFactor,
                    false,
                    false,
                    false,
                )
            )
        } else {
            player.clearTitle()
            player.removePotionEffect(PotionEffectType.SLOWNESS)
        }
    }

    fun shoot(player: Player, item: ItemStack) {
        val origin = player.location.add(0.0, 1.4, 0.0)
        player.setCooldown(item.type, cooldown)
        bullet.createBullet(player, origin, player.eyeLocation.direction)
        player.playSound(shootSound)
    }

    fun createGun(): Gun {
        val gun = Gun(this, maxAmmo, item.invoke())
        GunHandler.guns[gun.item] = gun
        return gun
    }

    class Builder {
        private var item: () -> ItemStack = { ItemStack(Material.GOLDEN_SHOVEL) }
        private var bullet: BulletType? = null
        private var cooldown: Int = 0
        private var maxAmmo: Int = 0
        private var zoomFactor: Int = 2
        private var scopeSprite: RegisteredSprite? = RegisteredSprites.SCOPE
        private var shootSound: Sound = Sounds.GUN_FIRE

        fun item(item: () -> ItemStack) = apply { this.item = item }
        fun bullet(bullet: BulletType) = apply { this.bullet = bullet }
        fun cooldown(cooldown: Int) = apply { this.cooldown = cooldown }
        fun maxAmmo(maxAmmo: Int) = apply { this.maxAmmo = maxAmmo }
        fun zoomFactor(zoomFactor: Int) = apply { this.zoomFactor = zoomFactor }
        fun scopeSprite(scopeSprite: RegisteredSprite?) = apply { this.scopeSprite = scopeSprite }
        fun shootSound(shootSound: Sound) = apply { this.shootSound = shootSound }

        fun build(): GunType {
            return GunType(
                item,
                bullet ?: error("BulletType must be provided"),
                cooldown,
                maxAmmo,
                zoomFactor,
                scopeSprite,
                shootSound,
            )
        }
    }

    companion object {
        inline fun buildGunType(block: Builder.() -> Unit) = Builder().apply(block).build()
    }
}