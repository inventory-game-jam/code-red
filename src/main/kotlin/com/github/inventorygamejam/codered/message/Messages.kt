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
    fun spriteWithSubtitle(sprite: RegisteredSprite, subtitle: String) =
        getServer().showTitle(title(sprite.component(), subtitle.mm))

    fun GameMatch.broadcast(message: String) = players.forEach { player -> player.sendMessage(message.mm) }
    fun GameMatch.title(message: String) = players.forEach { player -> player.showTitle(title(message.mm, empty())) }
    fun GameMatch.spriteWithSubtitle(sprite: RegisteredSprite, subtitle: String) =
        players.forEach { player -> player.showTitle(title(sprite.component(), subtitle.mm)) }

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
}
