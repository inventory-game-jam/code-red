package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.CodeRed
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit

inline fun runTask(crossinline block: () -> Unit) = Bukkit.getScheduler().runTask(CodeRed, Runnable { block() })
inline fun runTask(delay: Long, crossinline block: () -> Unit) =
    Bukkit.getScheduler().runTaskLater(CodeRed, Runnable { block() }, delay)