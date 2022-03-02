package net.sakuragame.eternal.kirraonlinereward

import net.sakuragame.eternal.kirraonlinereward.Profile.Companion.getProfile
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI
import org.bukkit.entity.Player
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost

@Suppress("SpellCheckingInspection")
object Database {

    private val defaultNumberPair by lazy {
        Pair(0, 0)
    }

    private const val PREFIX = "kirraonlinereward"

    private val host = KirraOnlineReward.conf.getHost("settings.database")

    private val dataSource by lazy {
        ClientManagerAPI.getDataManager().dataSource
    }

    private val tableOnlineReward = Table("${PREFIX}_table_number", host) {
        add("uid") {
            type(ColumnTypeSQL.INT) {
                options(ColumnOptionSQL.UNIQUE_KEY, ColumnOptionSQL.NOTNULL)
            }
        }
        add("day_of_month") {
            type(ColumnTypeSQL.INT)
        }
        add("online_minutes") {
            type(ColumnTypeSQL.INT)
        }
        add("online_receives") {
            type(ColumnTypeSQL.INT)
        }
    }


    init {
        tableOnlineReward.createTable(dataSource)
    }

    fun getOnlinePair(player: Player): Pair<Int, Int> {
        val uid = ClientManagerAPI.getUserID(player.uniqueId)
        val profile = player.getProfile() ?: return defaultNumberPair
        if (uid == -1) return defaultNumberPair
        val date = tableOnlineReward.select(dataSource) {
            where("uid" eq uid)
        }.firstOrNull {
            getInt("day_of_month")
        }
        if (date == null || profile.dayOfMonth != date) {
            return defaultNumberPair
        }
        val onlineMinutes = tableOnlineReward.select(dataSource) {
            where("uid" eq uid)
        }.first {
            getInt("online_minutes")
        }
        val onlineReceives = tableOnlineReward.select(dataSource) {
            where("uid" eq uid)
        }.first {
            getInt("online_receives")
        }
        return Pair(onlineMinutes, onlineReceives)
    }

    fun setOnlinePair(player: Player, pair: Pair<Int, Int>) {
        val uid = ClientManagerAPI.getUserID(player.uniqueId)
        val profile = player.getProfile() ?: return
        if (uid == -1) return
        val isFind = tableOnlineReward.find(dataSource) {
            where("uid" eq uid)
        }
        if (isFind) {
            tableOnlineReward.update(dataSource) {
                where { where("uid" eq uid) }
                set("day_of_month", profile.dayOfMonth)
                set("online_minutes", pair.first)
                set("online_receives", pair.second)
            }
        } else {
            tableOnlineReward.insert(dataSource, "uid", "day_of_month", "online_minutes", "online_receives") {
                value(uid, profile.dayOfMonth, pair.first, pair.second)
            }
        }
    }
}