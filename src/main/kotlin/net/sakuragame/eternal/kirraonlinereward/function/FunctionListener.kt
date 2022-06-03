package net.sakuragame.eternal.kirraonlinereward.function

import net.sakuragame.eternal.kirraonlinereward.Profile
import net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.DragonCoreCompat.updateDragonCoreVariable
import taboolib.common.platform.Schedule

object FunctionListener {

    @Schedule(period = 20 * 60, async = true)
    fun i() {
        Profile.profiles.values.forEach {
            it.onlineMinutes++
            updateDragonCoreVariable(it.player)
        }
    }
}