package com.github.inventorygamejam.codered.team

import com.github.inventorygamejam.codered.item.gun.Gun
import com.github.inventorygamejam.codered.util.BasicRegistry
import com.github.inventorygamejam.codered.util.name
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

object PlayerRoles : BasicRegistry<PlayerRole>() {
    val SNIPER = register("sniper", PlayerRole(ItemStack.of(Material.BOW).name("<aqua>Sniper"), Items.LEATHER_ARMOR, listOf(Items.GUN)))
    val ASSAULTER = register(
        "assaulter",
        PlayerRole(ItemStack.of(Material.IRON_SWORD).name("<red>Assaulter"), Items.LEATHER_ARMOR, listOf(Items.STONE_SWORD, Items.BOW, Items.SMALL_ARROWS))
    )
    val MEDIC = register(
        "medic",
        PlayerRole(ItemStack.of(Material.GOLDEN_APPLE).name("<green>Medic"), Items.LEATHER_ARMOR, listOf(Items.WOODEN_SWORD, Items.HEALING_POTION, Items.HEALING_POTION))
    )
    val SUPPORTER = register(
        "supporter",
        PlayerRole(ItemStack.of(Material.COBWEB).name("<blue>Supporter"), Items.LEATHER_ARMOR, listOf(Items.WOODEN_SWORD, Items.COBWEBS, Items.TNT, Items.BOW, Items.ARROWS))
    )

    object Items {
        val WOODEN_SWORD = ItemStack.of(Material.WOODEN_SWORD)
        val STONE_SWORD = ItemStack.of(Material.STONE_SWORD)
        val HEALING_POTION = ItemStack.of(Material.SPLASH_POTION).apply {
            editMeta(PotionMeta::class.java) { meta ->
                meta.basePotionType = PotionType.HEALING
            }
        }
        val GUN = Gun.item
        val COBWEBS = ItemStack.of(Material.COBWEB, 4)
        val TNT = ItemStack.of(Material.TNT)
        val LEATHER_ARMOR = listOf(
            ItemStack.of(Material.AIR),
            ItemStack.of(Material.LEATHER_CHESTPLATE),
            ItemStack.of(Material.LEATHER_LEGGINGS),
            ItemStack.of(Material.LEATHER_BOOTS)
        )
        val BOW = ItemStack.of(Material.BOW)
        val SMALL_ARROWS = ItemStack.of(Material.ARROW, 2)
        val ARROWS = ItemStack.of(Material.ARROW, 8)
    }
}