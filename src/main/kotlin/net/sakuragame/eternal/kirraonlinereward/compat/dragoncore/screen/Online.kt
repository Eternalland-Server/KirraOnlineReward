package net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.screen

import com.taylorswiftcn.megumi.uifactory.generate.function.Statements
import com.taylorswiftcn.megumi.uifactory.generate.type.ActionType
import com.taylorswiftcn.megumi.uifactory.generate.ui.component.base.SlotComp
import com.taylorswiftcn.megumi.uifactory.generate.ui.component.base.TextureComp
import com.taylorswiftcn.megumi.uifactory.generate.ui.screen.ScreenUI
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object Online {

    lateinit var UI: ScreenUI

    @Awake(LifeCycle.ENABLE)
    private fun i() {
        UI = ScreenUI("online").also {
            it.match = "hud"
            it.imports.add("reward")
            it.addComponent(TextureComp("body", "hud/online/body.png").apply {
                x = "5"
                y = "h*0.05"
                width = "48"
                height = "57"
                alpha = 0.7
                addAction(ActionType.Left_Click, Statements()
                    .add("global.reward_sidebar_visible = !global.reward_sidebar_visible;")
                    .build()
                )
            })
            it.addComponent(TextureComp("title", "0,0,0,30").apply {
                text = "func.PlaceholderAPI_Get('current_minutes')"
                x = "body.x"
                y = "body.y+43"
                width = "48"
                height = "14"
                addAction(ActionType.Left_Click, Statements()
                    .add("global.reward_sidebar_visible = !global.reward_sidebar_visible;")
                    .build()
                )
            })
            it.addComponent(SlotComp("icon", "online_reward_big_slot").apply {
                drawBackground = false
                x = "body.x+8"
                y = "body.y+9"
                scale = 2.0.toString()
                addAction(ActionType.Left_Click, Statements()
                    .add("global.reward_sidebar_visible = !global.reward_sidebar_visible;")
                    .build()
                )
            })
            it.addComponent(TextureComp("progress_bg", "hud/online/progress_bg.png").apply {
                x = "body.x+48"
                y = "body.y+10"
                width = "6"
                height = "39"
            })
            it.addComponent(TextureComp("progress_bar", "hud/online/progress_bar.png").apply {
                x = "progress_bg.x+1"
                y = "progress_bg.y+1"
                width = "4"
                height = "37"
                limitX = "progress_bg.x"
                limitY = "func.ceil(progress_bg.y + (1 - (global.reward_progress_current_minutes / global.reward_progress_upcoming_minutes)) * 39)"
                limitWidth = "6"
                limitHeight = "(global.reward_progress_current_minutes / global.reward_progress_upcoming_minutes) * 39"
            })
            it.addComponent(TextureComp("background", "hud/online/background.png").apply {
                x = "body.x+56"
                y = "body.y+4"
                width = "88"
                height = "129"
                alpha = 0.7
                visible = "global.reward_sidebar_visible == true"
            })
        }
    }
}