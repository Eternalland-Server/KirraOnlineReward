package net.sakuragame.eternal.kirraonlinereward

import ink.ptms.zaphkiel.ZaphkielAPI
import ink.ptms.zaphkiel.api.event.PluginReloadEvent
import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit

@Suppress("SpellCheckingInspection")
object KirraOnlineRewardAPI {

    data class RewardData(val minutes: Int, val item: ItemStack)

    val rewardItems = mutableListOf<RewardData>()

    @SubscribeEvent
    fun e(e: PluginReloadEvent.Item) {
        reload()
    }

    fun reload() {
        submit(async = true) {
            rewardItems.clear()
            fillItems()
        }
    }

    fun getUpcomingMinutes(player: Player): Int? {
        val profile = player.getProfile() ?: return null
        return rewardItems[profile.onlineReceives].minutes
    }

    fun getUpcomingReward(player: Player): ItemStack? {
        val profile = player.getProfile() ?: return null
        return rewardItems[profile.onlineReceives].item
    }

    private fun fillItems() {
        val section = KirraOnlineReward.conf.getConfigurationSection("settings.online-rewards") ?: return
        section.getKeys(false).forEach {
            val str = KirraOnlineReward.conf.getString("settings.online-rewards.$it") ?: return@forEach
            rewardItems += RewardData(it.toInt(), ZaphkielAPI.getItem(str)!!.toItemStack(null))
        }
        rewardItems.sortBy { it.minutes }
    }
}