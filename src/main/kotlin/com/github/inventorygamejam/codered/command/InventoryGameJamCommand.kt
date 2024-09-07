package com.github.inventorygamejam.codered.command

import com.github.inventorygamejam.codered.CodeRed
import com.github.inventorygamejam.codered.handler.MainObjectiveBuildHandler
import com.github.inventorygamejam.codered.message.Messages
import com.github.inventorygamejam.codered.message.Messages.debug
import com.github.inventorygamejam.codered.util.InventoryGameJamAPI
import com.github.inventorygamejam.codered.util.encodeToPrettyString
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlinx.serialization.json.Json
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser.offlinePlayerParser
import org.incendo.cloud.kotlin.coroutines.extension.suspendingHandler
import org.incendo.cloud.kotlin.extension.buildAndRegister
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.parser.standard.BooleanParser
import org.incendo.cloud.parser.standard.BooleanParser.booleanParser
import org.incendo.cloud.parser.standard.IntegerParser.integerParser
import org.incendo.cloud.parser.standard.StringParser.quotedStringParser
import org.incendo.cloud.parser.standard.StringParser.stringParser
import org.incendo.cloud.suggestion.SuggestionProvider.suggestingStrings

fun PaperCommandManager<CommandSourceStack>.registerIGJCommand() {
    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("debug").build {
            literal("api").build {
                literal("team").build {
                    literal("list").build {
                        suspendingHandler { ctx ->
                            val sender = ctx.sender().sender
                            sender.debug(Messages.DEBUG_TEAMS)
                            sender.debug(Json.encodeToPrettyString(InventoryGameJamAPI.getTeams()))
                        }
                    }
                }
            }
        }
    }

    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("debug").build {
            literal("api").build {
                literal("team").build {
                    literal("player").build {
                        literal("add").build {
                            required("teamName", quotedStringParser()) {
                                suggestionProvider(suggestingStrings(CodeRed.apiTeams.map { apiTeam -> "\"${apiTeam.name}\"" }))
                            }
                            required("player", offlinePlayerParser())

                            suspendingHandler { ctx ->
                                val sender = ctx.sender().sender
                                val team = ctx.get<String>("teamName")
                                val player = ctx.get<OfflinePlayer>("player")
                                InventoryGameJamAPI.addPlayerToTeam(team, player.uniqueId)

                                sender.debug(Messages.DEBUG_UPDATED_TEAMS)
                                sender.debug(Json.encodeToPrettyString(InventoryGameJamAPI.getTeams()))
                            }
                        }
                    }
                }
            }
        }
    }

    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("debug").build {
            literal("api").build {
                literal("team").build {
                    literal("player").build {
                        literal("remove").build {
                            required("teamName", quotedStringParser()) {
                                suggestionProvider(suggestingStrings(CodeRed.apiTeams.map { apiTeam -> "\"${apiTeam.name}\"" }))
                            }
                            required("player", offlinePlayerParser())

                            suspendingHandler { ctx ->
                                val sender = ctx.sender().sender
                                val team = ctx.get<String>("teamName")
                                val player = ctx.get<OfflinePlayer>("player")
                                InventoryGameJamAPI.removePlayerFromTeam(team, player.uniqueId)

                                sender.debug(Messages.DEBUG_UPDATED_TEAMS)
                                sender.debug(Json.encodeToPrettyString(InventoryGameJamAPI.getTeams()))
                            }
                        }
                    }
                }
            }
        }
    }

    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("debug").build {
            literal("api").build {
                literal("score").build {
                    literal("add").build {
                        literal("player").build {
                            required("player", offlinePlayerParser())
                            required("amount", integerParser())

                            suspendingHandler { ctx ->
                                val sender = ctx.sender().sender
                                val amount = ctx.get<Int>("amount")
                                val player = ctx.get<OfflinePlayer>("player")

                                InventoryGameJamAPI.addPlayerScore(player.uniqueId, amount)

                                sender.debug(Messages.DEBUG_SCORES_UPDATED)
                                sender.debug(Json.encodeToPrettyString(InventoryGameJamAPI.getTeams()))
                            }
                        }
                    }
                }
            }
        }
    }

    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("debug").build {
            literal("api").build {
                literal("score").build {
                    literal("add").build {
                        literal("team").build {
                            required("team", stringParser()) {
                                suggestionProvider(suggestingStrings(CodeRed.apiTeams.map { apiTeam -> "\"${apiTeam.name}\"" }))
                            }
                            required("amount", integerParser())

                            suspendingHandler { ctx ->
                                val sender = ctx.sender().sender
                                val amount = ctx.get<Int>("amount")
                                val team = ctx.get<String>("team")

                                InventoryGameJamAPI.addTeamScore(team, amount)

                                sender.debug(Messages.DEBUG_SCORES_UPDATED)
                                sender.debug(Json.encodeToPrettyString(InventoryGameJamAPI.getTeams()))
                            }
                        }
                    }
                }
            }
        }
    }

    buildAndRegister("igj") {
        permission("minecraft.op")
        literal("build").build {
            literal("mainobjective").build {
                required("enabled", booleanParser())

                handler { ctx ->
                    val player = ctx.sender().sender as? Player ?: return@handler
                    val enabled = ctx.get<Boolean>("enabled")

                    if (enabled) MainObjectiveBuildHandler.playersEnabled.add(player)
                    else MainObjectiveBuildHandler.playersEnabled.remove(player)

                    player.sendRichMessage("<green>Main objective builder toggled!")
                }
            }
        }
    }
}