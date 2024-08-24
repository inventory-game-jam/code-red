package com.github.inventorygamejam.codered.util

import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack
import com.github.inventorygamejam.codered.gui.resourcepack.CodeRedPack.negativeSpaces
import com.github.inventorygamejam.codered.gui.resourcepack.UIFont
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.ComponentBuilderApplicable
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

class TextBuilder {
    private val componentBuilder = Component.text()

    fun black(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.BLACK) else componentBuilder.color(NamedTextColor.BLACK)
        }

    fun darkRed(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.DARK_RED) else componentBuilder.color(NamedTextColor.DARK_RED)
        }

    fun darkPurple(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) {
                componentBuilder.colorIfAbsent(
                    NamedTextColor.DARK_PURPLE,
                )
            } else {
                componentBuilder.color(NamedTextColor.DARK_PURPLE)
            }
        }

    fun gold(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.GOLD) else componentBuilder.color(NamedTextColor.GOLD)
        }

    fun gray(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.GRAY) else componentBuilder.color(NamedTextColor.GRAY)
        }

    fun darkGray(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) {
                componentBuilder.colorIfAbsent(
                    NamedTextColor.DARK_GRAY,
                )
            } else {
                componentBuilder.color(NamedTextColor.DARK_GRAY)
            }
        }

    fun blue(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.BLUE) else componentBuilder.color(NamedTextColor.BLUE)
        }

    fun green(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.GREEN) else componentBuilder.color(NamedTextColor.GREEN)
        }

    fun aqua(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.AQUA) else componentBuilder.color(NamedTextColor.AQUA)
        }

    fun red(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.RED) else componentBuilder.color(NamedTextColor.RED)
        }

    fun lightPurple(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) {
                componentBuilder.colorIfAbsent(
                    NamedTextColor.LIGHT_PURPLE,
                )
            } else {
                componentBuilder.color(NamedTextColor.LIGHT_PURPLE)
            }
        }

    fun yellow(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.YELLOW) else componentBuilder.color(NamedTextColor.YELLOW)
        }

    fun white(colorIfAbsent: Boolean = false) =
        apply {
            if (colorIfAbsent) componentBuilder.colorIfAbsent(NamedTextColor.WHITE) else componentBuilder.color(NamedTextColor.WHITE)
        }

    fun append(component: ComponentLike) =
        apply {
            componentBuilder.append(component)
        }

    fun append(text: String?) =
        apply {
            append(Component.text(text ?: ""))
        }

    fun append(vararg components: ComponentLike) =
        apply {
            components.forEach(componentBuilder::append)
        }

    fun appendNewline() =
        apply {
            componentBuilder.appendNewline()
        }

    fun appendSpace() =
        apply {
            componentBuilder.appendSpace()
        }

    fun applicableApply(applicable: ComponentBuilderApplicable) =
        apply {
            componentBuilder.applicableApply(applicable)
        }

    fun applyDeep(factory: ComponentBuilder<*, *>.() -> Unit) =
        apply {
            componentBuilder.applyDeep(factory)
        }

    fun clickEvent(event: ClickEvent) =
        apply {
            componentBuilder.clickEvent(event)
        }

    fun color(color: TextColor) =
        apply {
            componentBuilder.color(color)
        }

    fun color(hex: String) =
        apply {
            componentBuilder.color(TextColor.fromHexString(hex))
        }

    fun colorIfAbsent(color: TextColor) =
        apply {
            componentBuilder.colorIfAbsent(color)
        }

    fun colorIfAbsent(hex: String) =
        apply {
            componentBuilder.colorIfAbsent(TextColor.fromHexString(hex))
        }

    fun decorate(decoration: TextDecoration) =
        apply {
            componentBuilder.decorate(decoration)
        }

    fun decorate(vararg decorations: TextDecoration) =
        apply {
            decorations.forEach(componentBuilder::decorate)
        }

    fun decoration(
        decoration: TextDecoration,
        value: Boolean,
    ) = apply {
        componentBuilder.decoration(decoration, value)
    }

    fun decorationIfAbsent(
        decoration: TextDecoration,
        value: TextDecoration.State,
    ) = apply {
        componentBuilder.decorationIfAbsent(decoration, value)
    }

    fun decorations(map: Map<TextDecoration, TextDecoration.State>) =
        apply {
            componentBuilder.decorations(map)
        }

    fun font(key: Key) =
        apply {
            componentBuilder.font(key)
        }

    fun font(key: net.radstevee.packed.core.key.Key) =
        apply {
            font(key.toString())
        }

    fun font(key: String) =
        apply {
            val keys = key.split(":")
            font(Key.key(keys.first(), keys.last()))
        }

    fun hoverEvent(event: HoverEventSource<*>) =
        apply {
            componentBuilder.hoverEvent(event)
        }

    fun italic() =
        apply {
            decorate(TextDecoration.ITALIC)
        }

    fun notItalic() =
        apply {
            decoration(TextDecoration.ITALIC, false)
        }

    fun bold() =
        apply {
            decorate(TextDecoration.BOLD)
        }

    fun notBold() =
        apply {
            decoration(TextDecoration.BOLD, false)
        }

    fun appendNegativeSpace(space: Int) =
        apply {
            append(
                buildText {
                    append(negativeSpaces.getChar(space).toString())
                    font(negativeSpaces.fontKey.toString())
                },
            )
        }

    fun font(font: UIFont) = apply {
        val key = font.packedFont.key
        font(key)
    }

    fun asComponent() = componentBuilder.asComponent()

    fun build() = componentBuilder.build()

    fun children() = componentBuilder.children()

    fun removeTextShadow() = apply {
        color("#4e5c24")
    }
}

inline fun buildText(block: TextBuilder.() -> Unit) = TextBuilder().apply(block).build()
