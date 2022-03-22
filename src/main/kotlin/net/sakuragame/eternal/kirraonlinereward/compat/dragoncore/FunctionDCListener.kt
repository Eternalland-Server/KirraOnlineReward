package net.sakuragame.eternal.kirraonlinereward.compat.dragoncore

import com.taylorswiftcn.megumi.uifactory.event.comp.UIFCompSubmitEvent
import net.sakuragame.eternal.dragoncore.api.event.YamlSendToPlayerEvent
import net.sakuragame.eternal.dragoncore.config.FolderType
import net.sakuragame.eternal.dragoncore.network.PacketSender
import net.sakuragame.eternal.justmessage.api.MessageAPI
import net.sakuragame.eternal.kirraonlinereward.KirraOnlineReward
import net.sakuragame.eternal.kirraonlinereward.KirraOnlineRewardAPI
import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.screen.Online
import net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.screen.Reward
import org.bukkit.Bukkit
import org.bukkit.Sound
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Baffle
import taboolib.module.chat.colored
import taboolib.platform.util.giveItem
import java.util.concurrent.TimeUnit

object FunctionDCListener {

    private val baffle by lazy {
        Baffle.of(1, TimeUnit.SECONDS)
    }

    @SubscribeEvent
    fun e(e: YamlSendToPlayerEvent) {
        val player = e.player
        PacketSender.sendYaml(player, FolderType.Gui, Reward.UI.id, Reward.UI.build(player))
        PacketSender.sendYaml(player, FolderType.Gui, Online.UI.id, Online.UI.build(player))
        PacketSender.sendOpenHud(player, Online.UI.id)
        DragonCoreCompat.updateDragonCoreVariable(player, init = true)
    }

    @SubscribeEvent
    fun e(e: UIFCompSubmitEvent) {
        if (!e.compID.startsWith("l_")) return
        if (e.params.getParam(0) != KirraOnlineReward.plugin.name) {
            return
        }
        val player = e.player
        val profile = player.getProfile() ?: return
        if (!baffle.hasNext(player.name)) {
            return
        }
        baffle.next(player.name)
        val number = e.params.getParamI(1)
        if (profile.getOnlineReceives() >= number) {
            return
        }
        val reward = KirraOnlineRewardAPI.rewardItems[number - 1]
        if (reward.minutes > profile.getOnlineMinutes()) {
            return
        }
        profile.setOnlineReceives(number)
        player.sendTitle("&6&l在线礼包".colored(), "&e您已领取在线 &f${reward.minutes} &e分钟礼包.".colored(), 10, 70, 20)
        player.giveItem(reward.item)
        player.playSound(player.location, Sound.BLOCK_NOTE_PLING, 1f, 1f)
        DragonCoreCompat.updateDragonCoreVariable(player)
    }
}