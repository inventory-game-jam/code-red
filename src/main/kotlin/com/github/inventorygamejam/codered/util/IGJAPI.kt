package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.CodeRed
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.headers

object IGJAPI {
    const val BASE_URL = "http://radsteve.net:3000"
    private val client = HttpClient(CIO)

    suspend fun uploadPack(bytes: ByteArray) {
        client.put("$BASE_URL/pack/code-red") {
            headers {
                bearerAuth(CodeRed.apiKey)
            }

            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("pack", bytes)
                    },
                ),
            )
        }
    }
}
