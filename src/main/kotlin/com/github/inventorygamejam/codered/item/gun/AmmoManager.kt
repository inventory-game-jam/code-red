package com.github.inventorygamejam.codered.item.gun

import com.github.inventorygamejam.codered.CodeRed
import net.kyori.adventure.text.format.NamedTextColor.GREEN
import net.kyori.adventure.text.format.NamedTextColor.RED
import net.kyori.adventure.text.format.NamedTextColor.YELLOW
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object AmmoManager {
    private val ammo = mutableMapOf<UUID, Int>()

    operator fun get(player: Player) = ammo[player.uniqueId] ?: MAX_AMMO

    operator fun get(uuid: UUID) = ammo[uuid] ?: MAX_AMMO

    operator fun set(
        player: Player,
        value: Int,
    ) {
        ammo[player.uniqueId] = value
    }

    operator fun set(
        uuid: UUID,
        value: Int,
    ) {
        ammo[uuid] = value
    }

    init {
        Bukkit.getScheduler().runTaskTimer(
            CodeRed,
            Runnable {
                val playersWithGun =
                    Bukkit.getOnlinePlayers().filter { player ->
                        player.inventory.itemInMainHand == Gun.item &&
                            this[player] < MAX_AMMO
                    }

                playersWithGun.forEach { player ->
                    val ammo = this[player]
                    this[player] = ammo + 1
                }
            },
            0L,
            10L,
        )
    }

    const val MAX_AMMO = 30
    val COLORS =
        buildMap {
            put(20..MAX_AMMO, GREEN)
            put(10..20, YELLOW)
            put(0..10, RED)
        }
}
