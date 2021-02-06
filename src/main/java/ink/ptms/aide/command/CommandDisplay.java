package ink.ptms.aide.command;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.command.base.*;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 坏黑
 * @since 2019-07-14 19:29
 */
@BaseCommand(
        name = "aideDisplay",
        aliases = {"aDisplay"},
        permission = "taboolib.admin"
)
public class CommandDisplay extends BaseMainCommand {

    @Override
    public String getCommandTitle() {
        return "§e§l----- §6§lAide Commands §e§l-----";
    }

    @SubCommand
    BaseSubCommand title = new BaseSubCommand() {

        @Override
        public String getDescription() {
            return "发送标题信息";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {
                    new Argument("目标"),
                    new Argument("标题"),
                    new Argument("淡入", false),
                    new Argument("停留", false),
                    new Argument("淡出", false)};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
            List<Player> players = Lists.newArrayList();
            if (args[0].equalsIgnoreCase("all")) {
                players.addAll(Bukkit.getOnlinePlayers());
            } else {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7目标 §f" + args[0] + " §7离线.");
                    return;
                }
                players.add(player);
            }
            String[] title = TLocale.Translate.setColored(args[1].replaceAll("__|\\\\s", " ")).split("\\\\r|\\\\n");
            players.forEach(player -> TLocale.Display.sendTitle(player,
                    title[0],
                    title.length > 1 ? title[1] : "",
                    args.length > 2 ? NumberConversions.toInt(args[2]) : 0,
                    args.length > 3 ? NumberConversions.toInt(args[3]) : 20,
                    args.length > 4 ? NumberConversions.toInt(args[4]) : 0));
        }
    };

    @SubCommand
    BaseSubCommand sound = new BaseSubCommand() {

        @Override
        public String getDescription() {
            return "发送音效";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {
                    new Argument("目标"),
                    new Argument("音效", () -> Arrays.stream(Sound.values()).map(Enum::name).collect(Collectors.toList())),
                    new Argument("音量", false),
                    new Argument("音调", false)};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
            List<Player> players = Lists.newArrayList();
            if (args[0].equalsIgnoreCase("all")) {
                players.addAll(Bukkit.getOnlinePlayers());
            } else {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7目标 §f" + args[0] + " §7离线.");
                    return;
                }
                players.add(player);
            }
            try {
                Sound sound = Sound.valueOf(args[1].toUpperCase());
                players.forEach(player -> player.playSound(player.getLocation(), sound, args.length > 2 ? NumberConversions.toFloat(args[2]) : 1f, args.length > 3 ? NumberConversions.toFloat(args[3]) : 1f));
            } catch (Throwable ignored) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7音效 §f" + args[1] + " §7无效.");
            }
        }
    };

    @SubCommand
    BaseSubCommand action = new BaseSubCommand() {

        @Override
        public String getDescription() {
            return "发送动作栏信息";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {
                    new Argument("目标"),
                    new Argument("内容")};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
            List<Player> players = Lists.newArrayList();
            if (args[0].equalsIgnoreCase("all")) {
                players.addAll(Bukkit.getOnlinePlayers());
            } else {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    sender.sendMessage("§7§l[§f§lAide§7§l] §7目标 §f" + args[0] + " §7离线.");
                    return;
                }
                players.add(player);
            }
            players.forEach(player -> TLocale.Display.sendActionBar(player, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
        }
    };
}
