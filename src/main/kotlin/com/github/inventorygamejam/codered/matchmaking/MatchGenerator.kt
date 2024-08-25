package com.github.inventorygamejam.codered.matchmaking

class MatchGenerator {
    private val startListeners: MutableList<MatchListener> = mutableListOf()
    private val endListeners: MutableList<MatchListener> = mutableListOf()

    /**
     * This is the method to register a listener to when a match gets created.
     * It takes in an anonymous function that will be executed as soon as the match is created and registered to this MatchGenerator
     * @see endMatch
     */
    fun onMatchStart(listener: MatchListener) {
        startListeners.add(listener)
    }

    /**
     * This is the method to register a listener to when a match ends.
     * It takes in an anonymous function that will be executed as soon as the match ends and is registered to this MatchGenerator
     * @see endMatch
     */
    fun onMatchEnd(listener: MatchListener) {
        endListeners.add(listener)
    }

    /**
     * This method should be called when a new Match is created (aka generated)
     * @see GameInstance
     */
    fun startMatch(match: GameMatch) {
        startListeners.forEach {
            it.onEvent(match)
        }
    }

    /**
     * This method should be called when a match ends
     */
    fun endMatch(match: GameMatch) {
        endListeners.forEach {
            it.onEvent(match)
        }
    }

    fun interface MatchListener {
        fun onEvent(match: GameMatch)
    }
}