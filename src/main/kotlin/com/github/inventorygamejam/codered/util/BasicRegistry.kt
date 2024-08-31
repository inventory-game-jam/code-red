package com.github.inventorygamejam.codered.util

/**
 * A basic string-object registry.
 */
open class BasicRegistry<T : Any>(
    /**
     * Whether this registry can be modified.
     */
    private val modifiable: Boolean = false,
) {
    private val _entries = mutableListOf<T>()
    private val _keys = mutableListOf<String>()
    private val keyToEntryMap = mutableMapOf<String, T>()
    private val entryToKeyMap = mutableMapOf<T, String>()

    /**
     * The amount of registered entries in this registry.
     */
    val size: Int get() = _entries.size

    /**
     * The registered entries in this registry.
     */
    val entries: List<T> get() = entries.toList()

    /**
     * The registered keys in this registry.
     */
    val keys: List<String> get() = _keys.toList()

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
        if (_keys.contains(key)) {
            modifiable ?: error("Key $key already registered")
            // remove old entry object
            val oldEntry = this[key]
            _entries.remove(oldEntry)
            entryToKeyMap.remove(oldEntry)
        }

        _entries.add(entry)
        _keys.add(key)

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
    operator fun get(index: Int): T? = _entries.getOrNull(index)

    /**
     * @return the index of the entry
     */
    fun indexOf(entry: T): Int = _entries.indexOf(entry)

    /**
     * Performs [action] on every registered entry.
     */
    fun forEach(action: (T) -> Unit) {
        _entries.forEach(action)
    }

    fun forEachIndexed(action: (Int, T) -> Unit) {
        _entries.forEachIndexed(action)
    }

    /**
     * Filters the entries list to the given [predicate].
     * @return a new list
     */
    fun firstOrNull(predicate: (T) -> Boolean): T? = _entries.firstOrNull(predicate)

    /**
     * Returns a random entry of the registry.
     */
    fun random(): T = _entries.random()
}
