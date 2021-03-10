package ink.ptms.aide.command.itemtool;

import com.google.common.collect.Lists;
import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.module.nms.NMS;
import io.izzel.taboolib.module.nms.nbt.NBTAttribute;
import io.izzel.taboolib.module.nms.nbt.NBTOperation;
import io.izzel.taboolib.util.Pair;
import io.izzel.taboolib.util.item.Equipments;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 坏黑
 * @since 2018-10-14 19:50
 */
public class CommandAttribute {

    static String[][] ARGUMENTS = {
            {
                    "damage", "health", "attackspeed", "luck", "armor", "speed", "knockback"
            },
            {
                    "mainhand", "offhand", "head", "chest", "legs", "feet"
            }
    };

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("addAttribute", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(ARGUMENTS[0]).filter(argument -> argument.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
                    }
                    if (args.length == 2) {
                        return Arrays.stream(ARGUMENTS[1]).filter(argument -> argument.startsWith(args[1].toLowerCase())).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 3) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (Items.asAttribute(args[0]) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid attribute.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Attribute &8+ &f" + args[0].toUpperCase() + ":" + args[1].toUpperCase() + ":" + args[2]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        List<NBTAttribute> attribute = NMS.handle().getAttribute(((Player) sender).getItemInHand());
                        Pair<Double, NBTOperation> operationKV = NBTOperation.fromSimple(args[2]);
                        attribute.add(NBTAttribute.create()
                                .name(Items.asAttribute(args[0]))
                                .amount(operationKV.getKey())
                                .operation(operationKV.getValue())
                                .slot(args[1].equalsIgnoreCase("all") ? null : Equipments.fromNMS(args[1])));
                        ((Player) sender).setItemInHand(NMS.handle().setAttribute(((Player) sender).getItemInHand(), attribute));
                    }
                }).build();

        CommandBuilder.create("removeAttribute", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(ARGUMENTS[0]).filter(argument -> argument.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
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
                    } else if (Items.asAttribute(args[0]) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid attribute.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "Attribute &8- &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        List<NBTAttribute> attribute = NMS.handle().getAttribute(((Player) sender).getItemInHand());
                        attribute.stream().filter(a -> a.getName().equals(Items.asAttribute(args[0]))).forEach(attribute::remove);
                        ((Player) sender).setItemInHand(NMS.handle().setAttribute(((Player) sender).getItemInHand(), attribute));
                    }
                }).build();
    }
}
