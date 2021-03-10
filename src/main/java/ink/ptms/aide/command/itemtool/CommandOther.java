package ink.ptms.aide.command.itemtool;

import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import ink.ptms.core.module.build.itemtool.util.Utils;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author 坏黑
 * @since 2018-10-14 10:46
 */
@SuppressWarnings("ALL")
public class CommandOther {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setSkullOwner", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof SkullMeta)) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "SkullOwner §8-> &f" + args[0]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        SkullMeta itemMeta = (SkullMeta) ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setOwner(args[0]);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("setArmorColor", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof LeatherArmorMeta)) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Color color = Utils.INSTANCE.asColor(args[0]);
                        Message.INSTANCE.send(sender, "ArmorColor §8-> &f" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        LeatherArmorMeta itemMeta = (LeatherArmorMeta) ((Player) sender).getItemInHand().getItemMeta();
                        itemMeta.setColor(color);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }
}
