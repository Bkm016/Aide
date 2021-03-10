package ink.ptms.aide.command.itemtool;

import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.NumberConversions;

/**
 * @author 坏黑
 * @since 2018-10-14 20:46
 */
public class CommandBook {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setAuthor", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "BookAuthor §8-> &f" + ArrayUtil.arrayJoin(args, 0));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        BookMeta itemMeta = (BookMeta) ((Player) sender).getItemInHand().getItemMeta();
                        assert itemMeta != null;
                        itemMeta.setAuthor(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("setGeneration", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (NumberConversions.toInt(args[0]) < 0 || NumberConversions.toInt(args[0]) > 3) {
                        Message.INSTANCE.send(sender, "&cInvalid generation.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "BookGeneration §8-> &f" + NumberConversions.toInt(args[0]));
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        BookMeta itemMeta = (BookMeta) ((Player) sender).getItemInHand().getItemMeta();
                        assert itemMeta != null;
                        switch (NumberConversions.toInt(args[0])) {
                            case 0:
                                itemMeta.setGeneration(BookMeta.Generation.ORIGINAL);
                                break;
                            case 1:
                                itemMeta.setGeneration(BookMeta.Generation.COPY_OF_ORIGINAL);
                                break;
                            case 2:
                                itemMeta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
                                break;
                            default:
                                itemMeta.setGeneration(BookMeta.Generation.TATTERED);
                                break;
                        }
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }
}
