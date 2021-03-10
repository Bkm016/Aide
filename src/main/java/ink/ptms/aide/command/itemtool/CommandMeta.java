package ink.ptms.aide.command.itemtool;

import com.google.common.collect.Lists;
import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.util.item.Items;
import io.izzel.taboolib.util.lite.Numbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 坏黑
 * @since 2018-10-12 23:09
 */
@SuppressWarnings("ALL")
public class CommandMeta {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("addEnchant", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("enchant")
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(Enchantment.values()).filter(type -> type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(Enchantment::getName).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!args[0].equalsIgnoreCase("all") && Items.asEnchantment(args[0].toUpperCase()) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid Enchantment type.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (NumberConversions.toInt(args[1]) <= 0) {
                        Message.INSTANCE.send(sender, "&cInvalid Enchantment level.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Enchant §8+ &f" + args[0].toUpperCase() + ":" + args[1]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        if (args[0].equalsIgnoreCase("all")) {
                            Arrays.stream(Enchantment.values()).forEach(enchantment -> itemMeta.addEnchant(enchantment, NumberConversions.toInt(args[1]), true));
                        } else {
                            itemMeta.addEnchant(Items.asEnchantment(args[0].toUpperCase()), NumberConversions.toInt(args[1]), true);
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("removeEnchant", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(Enchantment.values()).filter(type -> type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(Enchantment::getName).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!args[0].equalsIgnoreCase("all") && Items.asEnchantment(args[0].toUpperCase()) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid Enchantment type.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Enchant §8- &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        if (args[0].equalsIgnoreCase("all")) {
                            Arrays.stream(Enchantment.values()).forEach(itemMeta::removeEnchant);
                        } else {
                            itemMeta.removeEnchant(Items.asEnchantment(args[0].toUpperCase()));
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("addFlag", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(ItemFlag.values()).filter(type -> type.name().toUpperCase().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!args[0].equalsIgnoreCase("all") && Items.asItemFlag(args[0].toUpperCase()) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid ItemFlag.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Flag §8+ &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        if (args[0].equalsIgnoreCase("all")) {
                            itemMeta.addItemFlags(ItemFlag.values());
                        } else {
                            itemMeta.addItemFlags(Items.asItemFlag(args[0].toUpperCase()));
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("removeFlag", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(ItemFlag.values()).filter(type -> type.name().toUpperCase().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!args[0].equalsIgnoreCase("all") && Items.asItemFlag(args[0].toUpperCase()) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid ItemFlag.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Flag §8- &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        if (args[0].equalsIgnoreCase("all")) {
                            itemMeta.removeItemFlags(ItemFlag.values());
                        } else {
                            itemMeta.removeItemFlags(Items.asItemFlag(args[0].toUpperCase()));
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("setUnbreakable", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("unbreakable")
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
                    } else {
                        Message.INSTANCE.send(sender, "Unbreakable §8-> &f" + String.valueOf(Numbers.getBoolean(args[0])).toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setUnbreakable(Numbers.getBoolean(args[0]));
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }
}
