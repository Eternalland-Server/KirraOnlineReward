package net.sakuragame.eternal.kirraonlinereward

import ink.ptms.zaphkiel.ZaphkielAPI
import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit

@Suppress("SpellCheckingInspection")
object KirraOnlineRewardAPI {

    data class RewardData(val minutes: Int, val item: ItemStack)

    val rewardItems = mutableListOf<RewardData>()

    @Awake(LifeCycle.ACTIVE)
    fun reload() {
        submit(delay = 100L) {
            rewardItems.clear()
            fillItems()
        }
    }

    fun getUpcomingMinutes(player: Player): Int? {
        val profile = player.getProfile() ?: return null
        return rewardItems[profile.getOnlineReceives()].minutes
    }

    fun getUpcomingReward(player: Player): ItemStack? {
        val profile = player.getProfile() ?: return null
        return rewardItems[profile.getOnlineReceives()].item
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