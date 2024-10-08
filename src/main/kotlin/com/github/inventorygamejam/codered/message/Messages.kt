package com.github.inventorygamejam.codered.message

import com.github.inventorygamejam.codered.game.GameMatch
import com.github.inventorygamejam.codered.gui.resourcepack.RegisteredSprite
import com.github.inventorygamejam.codered.util.mm
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.title.Title.title
import org.bukkit.Bukkit.broadcast
import org.bukkit.Bukkit.getServer
import org.bukkit.command.CommandSender

object Messages {
    fun broadcast(message: String) = broadcast(message.mm)

    fun debug(message: String) = broadcast("<dark_gray>Debug |</dark_gray> $message".mm, "minecraft.op")
    fun CommandSender.debug(message: String) = sendRichMessage("<dark_gray>Debug | </dark_gray> $message")

    fun title(message: String) = getServer().showTitle(title(message.mm, empty()))
    fun spriteWithSubtitle(sprite: RegisteredSprite, subtitle: String) = getServer().showTitle(title(sprite.component(), subtitle.mm))

    fun GameMatch.broadcast(message: String) = map.world.sendMessage(message.mm)
    fun GameMatch.title(message: String) = map.world.showTitle(title(message.mm, empty()))
    fun GameMatch.spriteWithSubtitle(sprite: RegisteredSprite, subtitle: String) = map.world.showTitle(title(sprite.component(), subtitle.mm))

    const val CODE_RED = "<gray>Code <red>RED"
    const val CODE_YELLOW = "<gray>Code <yellow>YELLOW"
    const val CODE_GREEN = "<gray>Code <green>GREEN"
    const val OBJECTIVE_DESTROYED = "<red>The objective has been destroyed."
    const val ATTACKERS_WIN = "<gray>The <red>attackers</red> win."
    const val DEFENDERS_WIN = "<gray>The <blue>defenders</blue> win."
    const val DEBUG_TEAMS = "<blue>Current teams according to the IGJ API:"
    const val DEBUG_UPDATED_TEAMS = "<green>Teams updated! New teams:"
    const val DEBUG_SCORES_UPDATED = "<green>Scores updated! New scores:"
    const val ITEM_PICKED_UP = "<green>You picked up an item! Bring it to your teams vault!"
    const val ALL_ITEMS_COLLECTED =
        "<green>The attackers have picked up all items! It is now </green>$CODE_YELLOW.<br><red>The attackers will now have time to destroy the main objective."
    const val ITEM_INSERTED = "<green>Item has been added into the team vault!"
    const val ROLE_UPDATED = "<green>Role updated!"
    const val DEFENDERS_ESCAPED = "<green>The <blue>defenders</blue> have successfully escaped and evacuated."
    const val PLAYER_DEATH = "<red>%s was eliminated."
    const val PLAYER_DEATH_BY_PLAYER = "<red>%s was eliminated by <blue>%s."
    const val MAIN_OBJECTIVE_NOT_DESTROYABLE = "<red>In order to destroy the main objective, it needs to be $CODE_YELLOW!"
    private const val TIP_1 =
        "<aqua>To <green>win</green> as a <blue>defender</blue>, you need to collect all <gray>iron ingots</gray> and deposit them into your teams vault. The <red>attackers</red> need to prevent that."
    private const val TIP_2 =
        "<aqua>As an <red>attacker</red>, your main goal is to prevent the <blue>defenders</blue> from collecting all the <gray>iron ingots</gray>. You can do this by eliminating them or by destroying the main objective when it's <yellow>Code YELLOW</yellow>."
    private const val TIP_3 = "<aqua>Remember, buildings across the map may contain some loot. This loot can give you an advantage in the game."
    private const val TIP_4 =
        "<aqua>Coins are important! Each kill gets you <gold>20 coins</gold>, and a win gets your team <gold>100 coins</gold> each."
    val TIPS = listOf(TIP_1, TIP_2, TIP_3, TIP_4, TIP_4)
    const val TIME_TO_DESTROY_MAIN_OBJECTIVE = "<gray>The <red>attackers</red> will now have <aqua>60 seconds</aqua> to <red>destroy the main objective."
}
