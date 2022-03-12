package net.sakuragame.eternal.kirraonlinereward.function

import net.sakuragame.eternal.kirraonlinereward.KirraOnlineRewardAPI
import net.sakuragame.eternal.kirraonlinereward.Profile
import net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.DragonCoreCompat.updateDragonCoreVariable
import taboolib.common.platform.Schedule

object FunctionListener {

    @Schedule(period = 20 * 60, async = true)
    fun i() {
        Profile.profiles.values.forEach {
            val dataByReceiveCounts = KirraOnlineRewardAPI.rewardItems[it.getOnlineReceives()]
            if (dataByReceiveCounts.minutes >= it.getOnlineMinutes()) {
                it.plusOnlineMinutes(1)
                updateDragonCoreVariable(it.player)
            }
        }
    }
}