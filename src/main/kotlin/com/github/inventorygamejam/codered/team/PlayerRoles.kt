package com.github.inventorygamejam.codered.team

import com.github.inventorygamejam.codered.item.gun.GunTypes
import com.github.inventorygamejam.codered.util.BasicRegistry
import com.github.inventorygamejam.codered.util.editItemMeta
import com.github.inventorygamejam.codered.util.editItemMetaSpecific
import com.github.inventorygamejam.codered.util.withDestroyables
import com.github.inventorygamejam.codered.util.withName
import com.github.inventorygamejam.codered.util.withUnbreakable
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

object PlayerRoles : BasicRegistry<PlayerRole>() {
    val SNIPER = register(
        "sniper",
        PlayerRole(
            ItemStack.of(Material.BOW).withName("<aqua>Sniper"),
            Items.ARMOR_NONE, listOf(Items.AWP)
        )
    )
    val ASSAULTER = register(
        "assaulter",
        PlayerRole(
            ItemStack.of(Material.IRON_SWORD).withName("<red>Assaulter"),
            Items.LEATHER_ARMOR, listOf(Items.M16)
        )
    )
    val MEDIC = register(
        "medic",
        PlayerRole(
            ItemStack.of(Material.GOLDEN_APPLE).withName("<green>Medic"),
            Items.ARMOR_NONE, listOf(Items.HEALING_POTION, Items.HEALING_POTION)
        )
    )
    val SUPPORTER = register(
        "supporter",
        PlayerRole(
            ItemStack.of(Material.COBWEB).withName("<blue>Supporter"),
            Items.LEATHER_ARMOR, listOf(Items.GLOCK18, Items.COBWEBS)
        )
    )

    object Items {
        val WOODEN_SWORD = ItemStack.of(Material.WOODEN_SWORD).withUnbreakable()
        val STONE_SWORD = ItemStack.of(Material.STONE_SWORD).withUnbreakable()
        val HEALING_POTION = ItemStack.of(Material.SPLASH_POTION).editItemMetaSpecific<PotionMeta> { basePotionType = PotionType.HEALING }
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
            ItemStack.of(Material.AIR).withUnbreakable(),
            ItemStack.of(Material.LEATHER_CHESTPLATE).withUnbreakable(),
            ItemStack.of(Material.LEATHER_LEGGINGS).withUnbreakable(),
            ItemStack.of(Material.LEATHER_BOOTS).withUnbreakable(),
        )
        val BOW = ItemStack.of(Material.BOW).withUnbreakable()
        val SMALL_ARROWS = ItemStack.of(Material.ARROW, 2)
        val ARROWS = ItemStack.of(Material.ARROW, 8)
        val AIR = ItemStack.of(Material.AIR)
        val PICKAXE = ItemStack.of(Material.STONE_PICKAXE)
            .withDestroyables(Material.LIGHT_BLUE_STAINED_GLASS, Material.BLUE_WOOL)
            .withUnbreakable()
    }
}