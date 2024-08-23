package com.github.inventorygamejam.codered.util

import java.io.File
import java.security.MessageDigest
import kotlin.io.path.readBytes

// Thank you raydan for this
fun File.sha1() = toPath()
    .readBytes()
    .inputStream()
    .use {
        MessageDigest.getInstance("SHA-1").digest(it.readBytes())
    }.joinToString("") { "%02x".format(it) }