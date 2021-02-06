package ink.ptms.aide.command;

import com.google.common.base.Joiner;
import io.izzel.taboolib.TabooLibAPI;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.module.command.base.BaseCommand;
import io.izzel.taboolib.module.command.base.BaseMainCommand;
import io.izzel.taboolib.module.command.base.BaseSubCommand;
import io.izzel.taboolib.module.command.base.Argument;
import io.izzel.taboolib.module.command.base.SubCommand;
import io.izzel.taboolib.util.Commands;
import io.izzel.taboolib.util.Strings;
import io.izzel.taboolib.util.plugin.PluginLoadState;
import io.izzel.taboolib.util.plugin.PluginLoadStateType;
import io.izzel.taboolib.util.plugin.PluginUnloadState;
import io.izzel.taboolib.util.plugin.PluginUtils;
import io.izzel.taboolib.util.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sky
 * @since 2018-05-07 20:14
 */
@BaseCommand(
        name = "aidePlugin",
        aliases = {"aPlugin", "tPlugin"},
        permission = "taboolib.admin"
)
public class CommandPlugin extends BaseMainCommand {

    @Override
    public String getCommandTitle() {
        return "§e§l----- §6§lAide Commands §e§l-----";
    }

    @SubCommand(priority = 1)
    BaseSubCommand load = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "load";
        }

        @Override
        public String getDescription() {
            return "载入插件";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {new Argument("名称")};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            String name = ArrayUtil.arrayJoin(args, 0);
            if (PluginUtils.getPluginByName(name) != null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7已经被加载.");
            } else {
                PluginLoadState loadState;
                try {
                    loadState = PluginUtils.load(name);
                } catch (Exception e) {
                    loadState = new PluginLoadState(PluginLoadStateType.INVALID_PLUGIN, e.toString());
                }
                switch (loadState.getStateType()) {
                    case INVALID_DESCRIPTION: {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7无效.");
                        break;
                    }
                    case INVALID_PLUGIN: {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7载入失败: §f" + loadState.getMessage());
                        break;
                    }
                    case FILE_NOT_FOUND: {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7不存在.");
                        break;
                    }
                    default:
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7载入成功.");
                }
            }
        }
    };

    @SubCommand(priority = 2)
    BaseSubCommand unload = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "unload";
        }

        @Override
        public String getDescription() {
            return "卸载插件";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {new Argument("名称", () -> Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).collect(Collectors.toList()))};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            String name = ArrayUtil.arrayJoin(args, 0);
            Plugin plugin = PluginUtils.getPluginByName(name);
            if (plugin == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7未被加载.");
            } else {
                PluginUnloadState unloadState;
                try {
                    unloadState = PluginUtils.unload(plugin);
                } catch (Exception e) {
                    unloadState = new PluginUnloadState(true, e.toString());
                }
                if (unloadState.isFailed()) {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7卸载失败: §f" + unloadState.getMessage());
                } else {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7卸载成功.");
                }
            }
        }
    };

    @SubCommand(priority = 3)
    BaseSubCommand reload = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "reload";
        }

        @Override
        public String getDescription() {
            return "重载插件";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {new Argument("名称", () -> Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).collect(Collectors.toList()))};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            String name = ArrayUtil.arrayJoin(args, 0);
            Plugin plugin = PluginUtils.getPluginByName(name);
            if (plugin == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7未被加载.");
            } else {
                Commands.dispatchCommand(sender, "aidePlugin unload " + plugin.getName());
                Commands.dispatchCommand(sender, "aidePlugin load " + plugin.getName());
            }
        }
    };

    @SubCommand(priority = 4)
    BaseSubCommand info = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "info";
        }

        @Override
        public String getDescription() {
            return "插件信息";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {new Argument("名称", () -> Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).collect(Collectors.toList()))};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            String name = ArrayUtil.arrayJoin(args, 0);
            Plugin plugin = PluginUtils.getPluginByName(name);
            if (plugin == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7未被加载.");
            } else {
                try {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7信息:");
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7  版本: §f" + plugin.getDescription().getVersion());
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7  主类: §f" + plugin.getDescription().getMain());
                    String description = plugin.getDescription().getDescription();
                    if (!Strings.isEmpty(description)) {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7  描述: §f" + description);
                    }
                    String authors = String.join(", ", plugin.getDescription().getAuthors());
                    if (!Strings.isEmpty(authors)) {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7  作者: §f" + authors);
                    }
                    String depend = String.join(", ", plugin.getDescription().getDepend());
                    if (!Strings.isEmpty(depend)) {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7  依赖: §f" + depend);
                    }
                    String softDepend = String.join(", ", plugin.getDescription().getSoftDepend());
                    if (!Strings.isEmpty(softDepend)) {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7  兼容: §f" + softDepend);
                    }
                    if (!plugin.getDescription().getCommands().isEmpty()) {
                        sender.sendMessage("§7§l[§f§lAide§7§l] §7  命令:");
                        // 命令信息
                        for (Map.Entry<String, Map<String, Object>> commandEntry : plugin.getDescription().getCommands().entrySet()) {
                            sender.sendMessage("§7§l[§f§lAide§7§l] §7  - §f" + commandEntry.getKey());
                            sender.sendMessage("§7§l[§f§lAide§7§l] §7    别名: §f" + commandEntry.getValue().getOrDefault("aliases", "无"));
                            sender.sendMessage("§7§l[§f§lAide§7§l] §7    权限: §f" + commandEntry.getValue().getOrDefault("permission", "无"));
                        }
                    }
                } catch (Throwable e) {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7插件 §f" + name + " §7信息读取失败: §f" + e.getMessage());
                }
            }
        }
    };

    @SubCommand(priority = 5)
    BaseSubCommand list = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "list";
        }

        @Override
        public String getDescription() {
            return "插件列表";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[0];
        }

        @Override
        public void onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            List<String> pluginList = Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(PluginUtils::getFormattedName).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
            sender.sendMessage("§7§l[§f§lAide§7§l] §7当前运行 §f" + pluginList.size() + " §7个插件: §f" + Joiner.on(", ").join(pluginList));
        }
    };
}