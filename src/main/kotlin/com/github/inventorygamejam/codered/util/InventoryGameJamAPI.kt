package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.CodeRed
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIPlayer(
    val uuid: Uid,
    val score: Int,
)

@Serializable
data class APITeam(
    val name: String,
    @SerialName("total_score")
    val totalScore: Int,
    val players: List<APIPlayer>,
)

object InventoryGameJamAPI {
    const val BASE_URL = "http://radsteve.net:3000"
    private val client =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }

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

    suspend fun getTeams() =
        client
            .get(BASE_URL) {
                headers {
                    bearerAuth(CodeRed.apiKey)
                }
            }.body<List<APITeam>>()

    suspend fun addPlayerToTeam(
        team: String,
        player: Uid,
    ) = client
        .put("$BASE_URL/teams/$team/$player") {
            headers {
                bearerAuth(CodeRed.apiKey)
            }
        }.body<List<APITeam>>()

    suspend fun removePlayerFromTeam(
        team: String,
        player: Uid
    ) = client.delete("$BASE_URL/teams/$team/$player") {
        headers {
            bearerAuth(CodeRed.apiKey)
        }
    }.body<List<APITeam>>()

    suspend fun addPlayerScore(
        player: Uid,
        score: Int,
    ) = client.post("$BASE_URL/player_score/$player/$score") {
        headers {
            bearerAuth(CodeRed.apiKey)
        }
    }.body<List<APITeam>>()

    suspend fun addTeamScore(
        player: Uid,
        score: Int
    ) = client.post("$BASE_URL/team_score/$player/$score") {
        headers {
            bearerAuth(CodeRed.apiKey)
        }
    }.body<List<APITeam>>()
}
