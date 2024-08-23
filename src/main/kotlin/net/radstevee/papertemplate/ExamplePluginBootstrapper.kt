package net.radstevee.papertemplate

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext

class ExamplePluginBootstrapper : PluginBootstrap {
    override fun bootstrap(p0: BootstrapContext) {}

    override fun createPlugin(context: PluginProviderContext) = ExamplePlugin
}
