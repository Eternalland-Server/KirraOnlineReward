package net.sakuragame.eternal.kirraonlinereward

import net.sakuragame.eternal.justmessage.api.MessageAPI
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.platform.util.asLangText
import java.time.LocalDate

@Suppress("MemberVisibilityCanBePrivate")
class Profile(val player: Player) {

    var onlineMinutes = 0

    var onlineReceives = 0

    val dayOfMonth = localTime.dayOfMonth

    companion object {

        val localTime: LocalDate
            get() = LocalDate.now()!!

        @Schedule(async = true, period = 20 * 60 * 5)
        fun s() {
            profiles.values.forEach {
                it.save()
            }
        }

        val profiles = mutableMapOf<String, Profile>()

        fun Player.getProfile() = profiles.values.firstOrNull { it.player.uniqueId == uniqueId }

        @SubscribeEvent(EventPriority.HIGHEST)
        fun e(e: PlayerJoinEvent) {
            Profile(e.player).apply {
                profiles[e.player.name] = this
                read()
            }
        }

        @SubscribeEvent(EventPriority.LOWEST)
        fun e(e: PlayerKickEvent) {
            dataRecycle(e.player)
        }

        @SubscribeEvent(EventPriority.LOWEST)
        fun e(e: PlayerQuitEvent) {
            dataRecycle(e.player)
        }

        private fun dataRecycle(player: Player) {
            player.getProfile()?.apply {
                save()
                remove()
            }
        }
    }

    /**
     * 读取相关信息.
     */
    fun read() {
        submit(async = true) {
            val pair = Database.getOnlinePair(player)
            onlineMinutes = pair.first
            onlineReceives = pair.second
        }
    }

    /**
     * 保存相关信息.
     */
    fun save() {
        submit(async = true) {
            if (dayOfMonth != localTime.dayOfMonth) {
                onlineMinutes = 0
                onlineReceives = 0
                MessageAPI.sendActionTip(player, player.asLangText("message-online-reward-reset"))
            }
            Database.setOnlinePair(player, onlineMinutes to onlineReceives)
        }
    }

    /**
     * 销毁信息.
     */
    fun remove() = profiles.remove(player.name)
}
