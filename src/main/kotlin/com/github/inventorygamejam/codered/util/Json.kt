package com.github.inventorygamejam.codered.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val PRETTY_JSON = Json {
    prettyPrint = true
}

inline fun <reified T> Json.encodeToPrettyString(value: T) = PRETTY_JSON.encodeToString(value)