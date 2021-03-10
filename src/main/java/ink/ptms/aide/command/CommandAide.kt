package ink.ptms.aide.command

import ink.ptms.aide.bukkit.Sounds
import io.izzel.taboolib.PluginLoader
import io.izzel.taboolib.TabooLib
import io.izzel.taboolib.kotlin.Tasks
import io.izzel.taboolib.loader.PluginHandle
import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.CommandType
import io.izzel.taboolib.module.command.base.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author 坏黑
 * @since 2019-07-14 19:03
 */
@BaseCommand(name = "aide", permission = "taboolib.admin")
class CommandAide : BaseMainCommand() {

    @SubCommand(description = "查看运行库信息")
    fun about(sender: CommandSender, args: Array<String?>?) {
        sender.sendMessage("§7§l[§f§lAide§7§l] §7正在获取版本信息...")
        Tasks.task(true) {
            val newVersion = PluginHandle.getCurrentVersion()
            if (newVersion != null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7最新版本: §f" + newVersion[0])
            }
            sender.sendMessage("§7§l[§f§lAide§7§l] §7当前版本: §f" + TabooLib.getVersion())
            sender.sendMessage("§7§l[§f§lAide§7§l] §7由 §f" + PluginLoader.getFirstLoaded()!!.name + " §7启动")
        }
    }

    @SubCommand(description = "音效库", type = CommandType.PLAYER)
    fun sounds(sender: CommandSender, args: Array<String?>?) {
        Sounds(sender as Player).open()
    }
}