package ink.ptms.aide.command.itemtool;

import ink.ptms.aide.Aide;
import ink.ptms.aide.bukkit.ItemNMS;
import ink.ptms.core.module.build.itemtool.util.Message;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.entity.Player;

/**
 * @author 坏黑
 * @since 2018-10-16 23:11
 */
@SuppressWarnings("ALL")
public class CommandNBT {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("nbtInfo", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.getYES().play((Player) sender);
                        // Action
                        ItemNMS.Companion.INSTANCE.getITEM_NMS().sendItemNBT((Player) sender, ((Player) sender).getItemInHand());
                    }
                }).build();

        CommandBuilder.create("nbtClear", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .aliases("nbtClean")
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "NBT &8-> &4CLEAR");
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        ((Player) sender).setItemInHand(ItemNMS.Companion.INSTANCE.getITEM_NMS().clearNBT(((Player) sender).getItemInHand()));
                    }
                }).build();
    }
}
