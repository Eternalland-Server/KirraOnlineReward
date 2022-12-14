package net.sakuragame.eternal.kirraonlinereward.compat.dragoncore

import com.taylorswiftcn.megumi.uifactory.generate.function.Statements
import net.sakuragame.eternal.dragoncore.network.PacketSender
import net.sakuragame.eternal.kirraonlinereward.KirraOnlineRewardAPI
import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

object DragonCoreCompat {

    private val initStatements by lazy {
        Statements()
            .add("global.reward_sidebar_visible = false;")
            .build()!!
    }

    fun updateDragonCoreVariable(player: Player, init: Boolean = false) {
        submit(async = true, delay = 3L) {
            val profile = player.getProfile() ?: return@submit
            if (init) {
                PacketSender.sendRunFunction(player, "default", initStatements, true)
                KirraOnlineRewardAPI.rewardItems.forEachIndexed { index, data ->
                    PacketSender.putClientSlotItem(player, "online_reward_${index + 1}", data.item)
                }
            }
            val currentMinutes = if (profile.onlineMinutes == 0) 0.01 else profile.onlineMinutes.toDouble()
            val currentReceives = profile.onlineReceives
            val upcomingMinutes = KirraOnlineRewardAPI.getUpcomingMinutes(player) ?: return@submit
            PacketSender.sendRunFunction(player, "default", "global.reward_progress_current_minutes = $currentMinutes;", true)
            PacketSender.sendRunFunction(player, "default", "global.reward_progress_upcoming_minutes = $upcomingMinutes;", true)
            PacketSender.putClientSlotItem(player, "online_reward_big_slot", KirraOnlineRewardAPI.getUpcomingReward(player))
            val variableMap = mutableMapOf<String, String>().also { map ->
                if (upcomingMinutes <= currentMinutes.toInt()) {
                    map["body_texture"] = "hud/online/body_1.png"
                    map["current_minutes"] = "&a可领取".colored()
                } else {
                    map["body_texture"] = "hud/online/body.png"
                    map["current_minutes"] = "&e在线${upcomingMinutes}分钟".colored()
                }
                for (index in 1..KirraOnlineRewardAPI.rewardItems.size) {
                    map["background_texture_$index"] = "hud/online/0.png"
                    if (currentReceives < index) {
                        if (KirraOnlineRewardAPI.rewardItems[index - 1].minutes <= profile.onlineMinutes) {
                            map["background_texture_$index"] = "hud/online/1.png"
                            map["reward_${index}"] = "&a可领取".colored()
                        } else {
                            map["reward_${index}"] = "&7未解锁".colored()
                        }
                    } else {
                        map["reward_$index"] = "&7已领取".colored()
                    }
                }
            }
            PacketSender.sendSyncPlaceholder(player, variableMap)
        }
    }
}