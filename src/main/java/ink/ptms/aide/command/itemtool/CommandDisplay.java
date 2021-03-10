package ink.ptms.aide.command.itemtool;

import com.google.common.collect.Lists;
import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import ink.ptms.core.module.build.itemtool.util.Utils;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 坏黑
 * @since 2018-10-12 23:09
 */
@SuppressWarnings("ALL")
public class CommandDisplay {
    
    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setName", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("设置物品名称")
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
                        Message.INSTANCE.send(sender, "Name §8-> &f" + ArrayUtil.arrayJoin(args, 0));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setDisplayName(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("replaceName", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("替换物品名称")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        if (itemMeta.hasDisplayName()) {
                            itemMeta.setDisplayName(itemMeta.getDisplayName().replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("replaceNameRegex", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("替换物品名称（Regex）")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        if (itemMeta.hasDisplayName()) {
                            itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("addLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("添加物品描述")
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
                        Message.INSTANCE.send(sender, "Lore §8+ &f" + ArrayUtil.arrayJoin(args, 0));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        lore.add(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("insertLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("插入物品描述")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (!Utils.INSTANCE.isNumber(args[0]) || NumberConversions.toInt(args[0]) < 1) {
                        Message.INSTANCE.send(sender, "&cInvalid line.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8+ &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        while (lore.size() < NumberConversions.toInt(args[0])) {
                            lore.add("");
                        }
                        if (lore.size() == NumberConversions.toInt(args[0]) - 1) {
                            lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                        } else {
                            lore.add(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("setLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("设置物品描述")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (NumberConversions.toInt(args[0]) == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid line.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8-> &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        while (lore.size() < NumberConversions.toInt(args[0])) {
                            lore.add("");
                        }
                        lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("removeLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("移除物品描述")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (NumberConversions.toInt(args[0]) == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid line.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8-> &4REMOVE");
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        if (lore.size() >= NumberConversions.toInt(args[0])) {
                            lore.remove(NumberConversions.toInt(args[0]) - 1);
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("replaceLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("替换物品描述")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        for (int i = 0; i < lore.size(); i++) {
                            lore.set(i, lore.get(i).replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("replaceLoreRegex", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .description("替换物品描述（Regex）")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 2) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                        for (int i = 0; i < lore.size(); i++) {
                            lore.set(i, lore.get(i).replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                        }
                        itemMeta.setLore(lore);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("clearLore", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("cleanLore")
                .permission("itemTool.use")
                .description("清除物品描述")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Lore &8-> &4CLEAR");
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setLore(new ArrayList<>());
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }
}
