package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.gui.Sounds
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprites
import com.github.inventorygamejam.codered.item.CustomItem
import com.github.inventorygamejam.codered.util.buildText
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.title.Title.Times.times
import net.kyori.adventure.title.Title.title
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration.ofDays
import java.time.Duration.ofMillis

object Gun : CustomItem() {
    override var item = ItemStack.of(Material.GOLDEN_SHOVEL).apply {
        editMeta { meta ->
            meta.setCustomModelData(1)
            meta.displayName(buildText {
                append("Gun")
                notItalic()
            })
            meta.isUnbreakable = true
        }
    }
    override val key = NamespacedKey("codered", "gun")

    override fun onLeftClick(player: Player) {}

    override fun onCrouchChange(
        player: Player,
        value: Boolean,
    ) {
        if (value) {
            player.showTitle(
                title(
                    RegisteredSprites.SCOPE.component(),
                    empty(),
                    times(ofMillis(100), ofDays(10), ofMillis(100)),
                ),
            )

            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOWNESS,
                    -1,
                    2,
                    false,
                    false,
                    false,
                ),
            )
        } else {
            player.clearTitle()
            player.removePotionEffect(PotionEffectType.SLOWNESS)
        }
    }

    override fun onRightClick(player: Player) {
        val ammo = AmmoManager[player]
        if (ammo <= 0) {
            return
        }

        val origin = player.location.add(0.0, 1.4, 0.0)

        player.setCooldown(Material.GOLDEN_SHOVEL, 10)

        FireballBullet(player, origin, player.eyeLocation.direction)
        player.playSound(Sounds.GUN_FIRE)

        AmmoManager[player] = ammo - 1
    }

    init {
        init()
    }
}
