package ink.ptms.aide.command.itemtool;

import com.google.common.collect.Lists;
import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import ink.ptms.core.module.build.itemtool.util.Utils;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.util.item.Items;
import io.izzel.taboolib.util.lite.Numbers;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 坏黑
 * @since 2018-10-14 20:56
 */
@SuppressWarnings("ALL")
public class CommandFirework {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setFireworkPower", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK_ROCKET) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "FireworkPower §8-> &f" + NumberConversions.toInt(args[0]));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setPower(NumberConversions.toInt(args[0]));
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("addFireworkEffect", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .tab((sender, args) -> {
                    if (args.length == 1) {
                        return Arrays.stream(FireworkEffect.Type.values()).filter(type -> type.name().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                    }
                    return Lists.newArrayList();
                })
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK_ROCKET) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length < 3) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (asType(args[0]) == null) {
                        Message.INSTANCE.send(sender, "&cInvalid FireworkEffectType.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        FireworkEffect fireworkEffect = FireworkEffect.builder()
                                .with(asType(args[0]))
                                .withColor(Utils.INSTANCE.asColorArray(args[1]))
                                .withFade(Utils.INSTANCE.asColorArray(args[2]))
                                .flicker(args.length > 3 ? Numbers.getBoolean(args[3]) : false)
                                .trail(args.length > 4 ? Numbers.getBoolean(args[3]) : false)
                                .build();
                        FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.addEffect(fireworkEffect);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                        // Message
                        Message.INSTANCE.send(sender, "FireworkEffect §8+ &f" + fireworkEffect);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    }
                }).build();

        CommandBuilder.create("removeFireworkEffect", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK_ROCKET) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "FireworkEffect §8- &6LATEST");
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                        if (itemMeta.getEffectsSize() > 0) {
                            itemMeta.removeEffect(itemMeta.getEffectsSize() - 1);
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }

    private static FireworkEffect.Type asType(String name) {
        try {
            return FireworkEffect.Type.valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }
}
