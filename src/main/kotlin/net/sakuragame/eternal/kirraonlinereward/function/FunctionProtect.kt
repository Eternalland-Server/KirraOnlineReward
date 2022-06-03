package net.sakuragame.eternal.kirraonlinereward.function

import ink.ptms.zaphkiel.api.event.ItemFullyLoadEvent
import org.bukkit.event.player.PlayerLoginEvent
import taboolib.common.platform.event.SubscribeEvent

object FunctionProtect {

    var loaded = false

    fun load() {
        if (loaded) {
            return
        }
        loaded = true
    }

    @SubscribeEvent
    fun e(e: PlayerLoginEvent) {
        if (!loaded) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Server is loading.")
        }
    }
}