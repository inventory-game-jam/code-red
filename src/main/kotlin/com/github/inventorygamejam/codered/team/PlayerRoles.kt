package com.github.inventorygamejam.codered.team

import com.github.inventorygamejam.codered.item.gun.GunTypes
import com.github.inventorygamejam.codered.util.BasicRegistry
import com.github.inventorygamejam.codered.util.name
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

object PlayerRoles : BasicRegistry<PlayerRole>() {
    val SNIPER = register(
        "sniper",
        PlayerRole(
            ItemStack.of(Material.BOW).name("<aqua>Sniper"),
            Items.ARMOR_NONE, listOf(Items.AWP)
        )
    )
    val ASSAULTER = register(
        "assaulter",
        PlayerRole(
            ItemStack.of(Material.IRON_SWORD).name("<red>Assaulter"),
            Items.LEATHER_ARMOR, listOf(Items.M16)
        )
    )
    val MEDIC = register(
        "medic",
        PlayerRole(
            ItemStack.of(Material.GOLDEN_APPLE).name("<green>Medic"),
            Items.ARMOR_NONE, listOf(Items.HEALING_POTION, Items.HEALING_POTION)
        )
    )
    val SUPPORTER = register(
        "supporter",
        PlayerRole(
            ItemStack.of(Material.COBWEB).name("<blue>Supporter"),
            Items.LEATHER_ARMOR, listOf(Items.GLOCK18, Items.COBWEBS)
        )
    )

    object Items {
        val WOODEN_SWORD = ItemStack.of(Material.WOODEN_SWORD)
        val STONE_SWORD = ItemStack.of(Material.STONE_SWORD)
        val HEALING_POTION = ItemStack.of(Material.SPLASH_POTION).apply {
            editMeta(PotionMeta::class.java) { meta ->
                meta.basePotionType = PotionType.HEALING
            }
        }
        val GLOCK18 get() = GunTypes.GLOCK18.createGun().item
        val AWP get() = GunTypes.AWP.createGun().item
        val M16 get() = GunTypes.M16.createGun().item
        val COBWEBS = ItemStack.of(Material.COBWEB, 4)
        val TNT = ItemStack.of(Material.TNT)
        val ARMOR_NONE = listOf(
            ItemStack.of(Material.AIR),
            ItemStack.of(Material.AIR),
            ItemStack.of(Material.AIR),
            ItemStack.of(Material.AIR)
        )
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