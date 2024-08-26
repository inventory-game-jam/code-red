package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.CodeRed
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

fun Listener.unregister() = HandlerList.unregisterAll(this)
fun registerEvents(vararg listeners: Listener) = listeners.forEach { listener ->
    Bukkit.getPluginManager().registerSuspendingEvents(
        listener,
        CodeRed
    )
}