package ink.ptms.aide.command.itemtool;

import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import ink.ptms.core.module.build.itemtool.util.Utils;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

/**
 * @author 坏黑
 * @since 2018-10-12 23:09
 */
@SuppressWarnings("ALL")
public class CommandMaterial {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setMaterial", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("setType")
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (Items.asMaterial(args[0].toUpperCase()) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid Material.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Material §8-> &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ((Player) sender).getItemInHand().setType(Items.asMaterial(args[0].toUpperCase()));
                    }
                }).build();

        CommandBuilder.create("setDurability", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("setData", "setDamage")
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!Utils.INSTANCE.isNumber(args[0])) {
                        Message.INSTANCE.send(sender, "&cInvalid number.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Durability §8-> &f" + args[0]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ((Player) sender).getItemInHand().setDurability(NumberConversions.toShort(args[0]));
                    }
                }).build();

        CommandBuilder.create("setAmount", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!Utils.INSTANCE.isNumber(args[0])) {
                        Message.INSTANCE.send(sender, "&cInvalid number.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Amount §8-> &f" + args[0]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ((Player) sender).getItemInHand().setAmount(NumberConversions.toInt(args[0]));
                    }
                }).build();
    }
}
