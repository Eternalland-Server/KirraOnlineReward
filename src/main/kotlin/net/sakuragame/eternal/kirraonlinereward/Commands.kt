package net.sakuragame.eternal.kirraonlinereward

import net.sakuragame.eternal.dragoncore.network.PacketSender
import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import net.sakuragame.eternal.kirraonlinereward.compat.dragoncore.DragonCoreCompat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.chat.colored

@Suppress("SpellCheckingInspection")
@CommandHeader("KirraOnlineReward", aliases = ["oreward"])
object Commands {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val test1 = subCommand {
        dynamic(commit = "first") {
            dynamic(commit = "second") {
                execute<Player> { player, context, _ ->
                    player.sendMessage("context: ${context.get(1)}, ${context.get(2)}")
                    PacketSender.sendRunFunction(player, "default", "global.reward_progress_current_minutes = ${context.get(1)};", true)
                    PacketSender.sendRunFunction(player, "default", "global.reward_progress_upcoming_minutes = ${context.get(2)};", true)
                    player.sendMessage("ok!")
                }
            }
        }
    }

    @CommandBody
    val test2 = subCommand {
        dynamic(commit = "first") {
            dynamic(commit = "second") {
                execute<Player> { player, context, argument ->
                    player.sendMessage("context: ${context.get(1)}, $argument")
                    PacketSender.sendRunFunction(player, context.get(1), argument, true)
                    player.sendMessage("ok!")
                }
            }
        }
    }

    @CommandBody
    val test3 = subCommand {
        dynamic(commit = "minutes") {
            dynamic(commit = "receives") {
                execute<Player> { player, context, argument ->
                    val profile = player.getProfile() ?: return@execute
                    player.sendMessage("context: ${context.get(1)}, $argument")
                    profile.setOnlineMinutes(context.get(1).toInt())
                    profile.setOnlineReceives(argument.toInt())
                    DragonCoreCompat.updateDragonCoreVariable(player)
                    player.sendMessage("ok!")
                }
            }
        }
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            KirraOnlineRewardAPI.reload()
            sender.sendMessage("&c[System] &7重载完成.".colored())
        }
    }
}