package net.sakuragame.eternal.kirraonlinereward

import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin

@Suppress("SpellCheckingInspection")
object KirraOnlineReward : Plugin() {

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    @Config("config.yml")
    lateinit var conf: Configuration
        private set
}