package ink.ptms.aide.command;

import io.izzel.taboolib.module.command.base.*;
import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.Commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author sky
 * @since 2018-07-04 21:32
 */
@BaseCommand(
        name = "AideExecute",
        aliases = {"aExecute", "tExecute"},
        permission = "taboolib.admin"
)
public class CommandExecute extends BaseMainCommand {

    @Override
    public String getCommandTitle() {
        return "§e§l----- §6§lAide Commands §e§l-----";
    }

    @SubCommand(priority = 1)
    BaseSubCommand chat = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "chat";
        }

        @Override
        public String getDescription() {
            return "使玩家发送信息";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {new Argument("玩家"), new Argument("信息")};
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7玩家 §f" + args[0] + " §7离线.");
                return;
            }
            player.chat(ArrayUtil.arrayJoin(args, 1));
        }
    };

    @SubCommand(priority = 1)
    BaseSubCommand command = new BaseSubCommand() {
        @Override
        public String getLabel() {
            return "command";
        }

        @Override
        public String[] getAliases() {
            return new String[] {"cmd"};
        }

        @Override
        public String getDescription() {
            return "使目标执行命令";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {
                    new Argument("目标"),
                    new Argument("命令")
            };
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            if (args[0].equalsIgnoreCase("console")) {
                Commands.dispatchCommand(Bukkit.getConsoleSender(), ArrayUtil.arrayJoin(args, 1));
                return;
            }
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7玩家 §f" + args[0] + " §7离线.");
                return;
            }
            Commands.dispatchCommand(player, ArrayUtil.arrayJoin(args, 1));
        }
    };

    @SubCommand(priority = 2)
    BaseSubCommand commandAsOp = new BaseSubCommand() {

        @Override
        public String getLabel() {
            return "commandAsOp";
        }

        @Override
        public String[] getAliases() {
            return new String[] {"op"};
        }

        @Override
        public String getDescription() {
            return "使目标执行命令 (忽略权限)";
        }

        @Override
        public Argument[] getArguments() {
            return new Argument[] {
                    new Argument("目标"),
                    new Argument("命令")

            };
        }

        @Override
        public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            if (args[0].equalsIgnoreCase("console")) {
                Commands.dispatchCommand(Bukkit.getConsoleSender(), ArrayUtil.arrayJoin(args, 1));
                return;
            }
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("§7§l[§f§lAide§7§l] §7玩家 §f" + args[0] + " §7离线.");
                return;
            }
            boolean isOp = player.isOp();
            player.setOp(true);
            try {
                Commands.dispatchCommand(player, ArrayUtil.arrayJoin(args, 1));
            } catch (Throwable ignored) {
            }
            player.setOp(isOp);
        }
    };
}