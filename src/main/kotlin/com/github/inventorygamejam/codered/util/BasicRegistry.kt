package com.github.inventorygamejam.codered.util

import java.util.function.Consumer

/**
 * A basic string-object registry.
 */
open class BasicRegistry<T : Any>(
    /**
     * Whether this registry can be modified.
     */
    private val modifiable: Boolean = false,
) {
    private val entries = mutableListOf<T>()
    private val keys = mutableListOf<String>()
    private val keyToEntryMap = mutableMapOf<String, T>()
    private val entryToKeyMap = mutableMapOf<T, String>()

    /**
     * The amount of registered entries in this registry.
     */
    val size: Int get() = entries.size

    /**
     * The default value of this registry.
     */
    open val defaultValue: T? = null

    /**
     * Registers [entry] to the registry under [key].
     * @return the passed [entry]
     */
    fun register(
        key: String,
        entry: T,
    ): T {
        if (keys.contains(key)) {
            modifiable ?: error("Key $key already registered")
            // remove old entry object
            val oldEntry = this[key]
            entries.remove(oldEntry)
            entryToKeyMap.remove(oldEntry)
        }

        entries.add(entry)
        keys.add(key)

        keyToEntryMap[key] = entry
        entryToKeyMap[entry] = key

        return entry
    }

    /**
     * @return the value that is assigned [key], or `null` if it is not registered
     */
    open operator fun get(key: String): T? = keyToEntryMap[key]

    /**
     * @return the key assigned to [entry], or `null` if it is not registered
     */
    operator fun get(entry: T): String? = entryToKeyMap[entry]

    /**
     * @return the value that is assigned to the index [index], or null if one is not present
     */
    operator fun get(index: Int): T? = entries.getOrNull(index)

    /**
     * @return the index of the entry
     */
    fun indexOf(entry: T): Int = entries.indexOf(entry)

    /**
     * Performs [action] on every registered entry.
     */
    fun forEach(action: (T) -> Unit) {
        entries.forEach(action)
    }

    fun forEachIndexed(action: (Int, T) -> Unit) {
        entries.forEachIndexed(action)
    }

    /**
     * Filters the entries list to the given [predicate].
     * @return a new list
     */
    fun firstOrNull(predicate: (T) -> Boolean): T? = entries.firstOrNull(predicate)

    /**
     * Returns a random entry of the registry.
     */
    fun random(): T = entries.random()
}
