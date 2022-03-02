package net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.screen

import com.taylorswiftcn.megumi.uifactory.generate.function.SubmitParams
import com.taylorswiftcn.megumi.uifactory.generate.type.ActionType
import com.taylorswiftcn.megumi.uifactory.generate.ui.component.base.LabelComp
import com.taylorswiftcn.megumi.uifactory.generate.ui.component.base.SlotComp
import com.taylorswiftcn.megumi.uifactory.generate.ui.component.base.TextureComp
import com.taylorswiftcn.megumi.uifactory.generate.ui.screen.ScreenUI
import net.sakuragame.eternal.kirraonlinereward.KirraOnlineReward
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object Reward {

    lateinit var UI: ScreenUI

    @Awake(LifeCycle.ENABLE)
    private fun i() {
        UI = ScreenUI("reward").also {
            it.addComponent(TextureComp("o_1", "hud/online/0.png").apply {
                x = "background.x+8"
                y = "background.y+5"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_1", "hud/online/frame_a.png").apply {
                x = "o_1.x"
                y = "o_1.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_1", "online_reward_1").apply {
                drawBackground = false
                x = "f_1.x"
                y = "f_1.y"
                visible = "background.visible"
            })
            it.addComponent(LabelComp("l_1", "func.PlaceholderAPI_Get('reward_1')").apply {
                x = "o_1.x+48"
                y = "o_1.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("1")
                })
            })
            it.addComponent(TextureComp("o_2", "hud/online/1.png").apply {
                x = "o_1.x"
                y = "o_1.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("o_3", "0,0,0,0").apply {
                x = "o_2.x"
                y = "o_2.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("o_4", "0,0,0,0").apply {
                x = "o_3.x"
                y = "o_3.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("o_5", "0,0,0,0").apply {
                x = "o_4.x"
                y = "o_4.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("o_6", "0,0,0,0").apply {
                x = "o_5.x"
                y = "o_5.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("o_7", "0,0,0,0").apply {
                x = "o_6.x"
                y = "o_6.y+17"
                width = "75"
                height = "16"
                visible = "background.visible"
            })

            it.addComponent(TextureComp("f_2", "hud/online/frame_a.png").apply {
                x = "o_2.x"
                y = "o_2.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_3", "hud/online/frame_a.png").apply {
                x = "o_3.x"
                y = "o_3.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_4", "hud/online/frame_a.png").apply {
                x = "o_4.x"
                y = "o_4.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_5", "hud/online/frame_a.png").apply {
                x = "o_5.x"
                y = "o_5.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_6", "hud/online/frame_a.png").apply {
                x = "o_6.x"
                y = "o_6.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })
            it.addComponent(TextureComp("f_7", "hud/online/frame_a.png").apply {
                x = "o_7.x"
                y = "o_7.y"
                width = "16"
                height = "16"
                visible = "background.visible"
            })

            it.addComponent(SlotComp("s_2", "online_reward_2").apply {
                x = "f_2.x"
                y = "f_2.y"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_3", "online_reward_3").apply {
                x = "f_3.x"
                y = "f_3.y"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_4", "online_reward_4").apply {
                x = "f_4.x"
                y = "f_4.y"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_5", "online_reward_5").apply {
                x = "f_5.x"
                y = "f_5.y"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_6", "online_reward_6").apply {
                x = "f_6.x"
                y = "f_6.y"
                visible = "background.visible"
            })
            it.addComponent(SlotComp("s_7", "online_reward_7").apply {
                x = "f_7.x"
                y = "f_7.y"
                visible = "background.visible"
            })

            it.addComponent(LabelComp("l_2", "func.PlaceholderAPI_Get('reward_2')").apply {
                x = "o_1.x+48"
                y = "o_2.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("2")
                })
            })
            it.addComponent(LabelComp("l_3", "func.PlaceholderAPI_Get('reward_3')").apply {
                x = "o_3.x+48"
                y = "o_3.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("3")
                })
            })
            it.addComponent(LabelComp("l_4", "func.PlaceholderAPI_Get('reward_4')").apply {
                x = "o_4.x+48"
                y = "o_4.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("4")
                })
            })
            it.addComponent(LabelComp("l_5", "func.PlaceholderAPI_Get('reward_5')").apply {
                x = "o_5.x+48"
                y = "o_5.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("5")
                })
            })
            it.addComponent(LabelComp("l_6", "func.PlaceholderAPI_Get('reward_6')").apply {
                x = "o_6.x+48"
                y = "o_6.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("6")
                })
            })
            it.addComponent(LabelComp("l_7", "func.PlaceholderAPI_Get('reward_7')").apply {
                x = "o_7.x+48"
                y = "o_7.y+4"
                visible = "background.visible"
                addAction(ActionType.Left_Click, SubmitParams().apply {
                    addValue(KirraOnlineReward.plugin.name)
                    addValue("7")
                })
            })
        }
    }
}
