package net.sakuragame.eternal.kirraonlinereward

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicInteger

@Suppress("MemberVisibilityCanBePrivate")
class Profile(val player: Player) {

    data class ProfileOnlineData(val onlineMinutes: AtomicInteger = AtomicInteger(0), val onlineReceives: AtomicInteger = AtomicInteger(0))

    val dayOfMonth = localDate.dayOfMonth

    val onlineData = ProfileOnlineData()

    companion object {

        val localDate by lazy {
            LocalDate.now()!!
        }

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
            onlineData.apply {
                onlineMinutes.set(pair.first)
                onlineReceives.set(pair.second)
            }
        }
    }

    /**
     * 保存相关信息.
     */
    fun save() {
        submit(async = true) {
            onlineData.apply {
                Database.setOnlinePair(player, Pair(onlineMinutes.get(), onlineReceives.get()))
            }
        }
    }

    /**
     * 销毁信息.
     */
    fun remove() = profiles.remove(player.name)

    fun getOnlineMinutes() = onlineData.onlineMinutes.get()

    fun plusOnlineMinutes(toPlus: Int) = onlineData.onlineMinutes.set(getOnlineMinutes() + toPlus)

    fun setOnlineMinutes(int: Int) = onlineData.onlineMinutes.set(int)

    fun getOnlineReceives() = onlineData.onlineReceives.get()

    fun plusOnlineReceives(toPlus: Int) = onlineData.onlineReceives.set(getOnlineReceives() + toPlus)

    fun setOnlineReceives(int: Int) = onlineData.onlineReceives.set(int)
}
