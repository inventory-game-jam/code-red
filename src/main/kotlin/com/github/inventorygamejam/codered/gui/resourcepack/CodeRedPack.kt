package com.github.inventorygamejam.codered.gui.resourcepack

import com.github.inventorygamejam.codered.CodeRed.ghPat
import com.github.inventorygamejam.codered.CodeRed.ghUsername
import com.github.inventorygamejam.codered.util.InventoryGameJamAPI
import com.github.inventorygamejam.codered.util.sha1
import com.github.syari.kgit.KGit
import net.kyori.adventure.resource.ResourcePackInfo.resourcePackInfo
import net.kyori.adventure.resource.ResourcePackRequest.addingRequest
import net.radstevee.packed.core.asset.impl.GitAssetResolutionStrategy
import net.radstevee.packed.core.key.Key
import net.radstevee.packed.core.pack.PackFormat
import net.radstevee.packed.core.pack.ResourcePackBuilder.Companion.resourcePack
import net.radstevee.packed.negativespaces.NegativeSpaces
import org.bukkit.entity.Player
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File
import java.net.URI

object CodeRedPack {
    const val PACK_URL = "http://radsteve.net:3000/packs/code-red"

    fun Player.sendPack() {
        sendResourcePacks(
            addingRequest(
                resourcePackInfo()
                    .hash(hash)
                    .uri(URI(PACK_URL)),
            ),
        )
    }

    val negativeSpaces = NegativeSpaces(Key("codered", "space"), -512..512)
    val assetPath = File(System.getProperty("java.io.tmpdir"), "code-red-assets")
    val pack =
        resourcePack {
            meta {
                format = PackFormat.LATEST
                description = "Inventory Game Jam: Code Red"
                outputDir = File(System.getProperty("java.io.tmpdir"), "code-red-pack")
            }

            assetResolutionStrategy =
                GitAssetResolutionStrategy(
                    KGit.cloneRepository {
                        setURI("https://github.com/inventory-game-jam/code-red-assets.git")
                        assetPath.deleteRecursively()
                        setDirectory(assetPath)

                        setCredentialsProvider(UsernamePasswordCredentialsProvider(ghUsername, ghPat))
                    },
                )

            install(negativeSpaces)
            install(ChevyFontsPackedPlugin)
        }
    var bytes = byteArrayOf()
    var hash = ""

    fun init() {
        RegisteredSprites.init()
        RegisteredFonts.init()
    }

    fun save() {
        pack.save(deleteOld = true)
        val file = File.createTempFile("code-red-pack-", ".zip")
        pack.createZip(file)

        hash = file.sha1()
        bytes = file.readBytes()

        file.delete()
    }

    suspend fun upload() {
        InventoryGameJamAPI.uploadPack(bytes)
    }
}
