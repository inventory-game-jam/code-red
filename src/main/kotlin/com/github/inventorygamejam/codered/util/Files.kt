package com.github.inventorygamejam.codered.util

import java.io.File
import java.security.MessageDigest
import kotlin.io.path.readBytes

// Thank you raydan for this
fun File.sha1() = toPath()
    .readBytes()
    .inputStream()
    .use { stream ->
        MessageDigest.getInstance("SHA-1").digest(stream.readBytes())
    }.joinToString("") { sha -> "%02x".format(sha) }