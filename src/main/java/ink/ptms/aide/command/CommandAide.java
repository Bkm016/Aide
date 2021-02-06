package ink.ptms.aide.command;

import ink.ptms.aide.Aide;
import io.izzel.taboolib.PluginLoader;
import io.izzel.taboolib.TabooLib;
import io.izzel.taboolib.loader.PluginHandle;
import io.izzel.taboolib.module.command.base.BaseCommand;
import io.izzel.taboolib.module.command.base.BaseMainCommand;
import io.izzel.taboolib.module.command.base.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author 坏黑
 * @since 2019-07-14 19:03
 */
@BaseCommand(name = "aide", permission = "taboolib.admin")
public class CommandAide extends BaseMainCommand {

    @SubCommand(description = "查看运行库信息")
    public void about(CommandSender sender, String[] args) {
        sender.sendMessage("§7§l[§f§lAide§7§l] §7正在获取版本信息...");
        Bukkit.getScheduler().runTaskAsynchronously(Aide.getInstance().getPlugin(), () -> {
            String[] newVersion = PluginHandle.getCurrentVersion();
            if (newVersion != null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7最新版本: §f" + newVersion[0]);
            }
            sender.sendMessage("§7§l[§f§lAide§7§l] §7当前版本: §f" + TabooLib.getVersion());
            assert PluginLoader.getFirstLoaded() != null;
            sender.sendMessage("§7§l[§f§lAide§7§l] §7由 §f" + PluginLoader.getFirstLoaded().getName() + " §7启动");
        });
    }
}
