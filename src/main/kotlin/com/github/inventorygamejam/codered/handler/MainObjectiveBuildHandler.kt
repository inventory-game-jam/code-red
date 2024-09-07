package com.github.inventorygamejam.codered.handler

import com.github.inventorygamejam.codered.message.Messages.debug
import com.github.inventorygamejam.codered.util.registerEvents
import org.bukkit.entity.Entity
import org.bukkit.entity.Marker
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object MainObjectiveBuildHandler : Listener {
    const val MAIN_OBJECTIVE_TAG_KEY = "codered.mainObjective"
    val playersEnabled = mutableListOf<Player>()
    init {
        registerEvents(this)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.player !in playersEnabled) return
        val location = event.block.location
        debug("Block placed")
        if (location.world.getNearbyEntitiesByType(Marker::class.java, location, 1.0).isNotEmpty()) return
        debug("Block placed, spawning marker at $location")

        location.world.spawn(location, Marker::class.java) { marker ->
            marker.addScoreboardTag(MAIN_OBJECTIVE_TAG_KEY)
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player !in playersEnabled) return
        val location = event.block.location
        val markers = location.world.getNearbyEntitiesByType(Marker::class.java, location, 1.0)
        debug("Block broken")
        if (markers.isEmpty()) return
        debug("Marker exists, deleting")

        val mainObjectiveMarkers = markers.filter { marker -> MAIN_OBJECTIVE_TAG_KEY in marker.scoreboardTags }
        mainObjectiveMarkers.forEach(Entity::remove)
    }
}