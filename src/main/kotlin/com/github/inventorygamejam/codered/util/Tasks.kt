package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.CodeRed
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

inline fun runTask(crossinline block: () -> Unit) = Bukkit.getScheduler().runTask(CodeRed, Runnable { block() })
inline fun runTask(delay: Long, crossinline block: () -> Unit) =
    Bukkit.getScheduler().runTaskLater(CodeRed, Runnable { block() }, delay)

inline fun runTaskRepeating(delay: Long = 0, period: Long = 0, crossinline block: BukkitRunnable.() -> Unit) = object : BukkitRunnable() {
    override fun run() {
        block()
    }
}.runTaskTimer(CodeRed, delay, period)