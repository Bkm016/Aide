package ink.ptms.core.module.build.itemtool.util

import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.util.lite.SoundPack
import org.bukkit.command.CommandSender

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:11
 */
object Message {

    var ITEM_EDIT = SoundPack("UI_BUTTON_CLICK-1-1")
    var YES = SoundPack("ENTITY_EXPERIENCE_ORB_PICKUP-1-2")
    var NO = SoundPack("ENTITY_VILLAGER_NO-1-1")

    fun send(sender: CommandSender, message: String) {
        if (sender.hasPermission("ItemTool.notify")) {
            sender.sendMessage(" §8[§3§k§l§o|§8] §7" + TLocale.Translate.setColored(message))
        }
    }
}
