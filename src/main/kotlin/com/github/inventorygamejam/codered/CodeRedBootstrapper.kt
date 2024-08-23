package com.github.inventorygamejam.codered

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext

class CodeRedBootstrapper : PluginBootstrap {
    override fun bootstrap(p0: BootstrapContext) {}

    override fun createPlugin(context: PluginProviderContext) = CodeRed
}
