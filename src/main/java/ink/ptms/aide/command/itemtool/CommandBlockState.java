package ink.ptms.aide.command.itemtool;

import ink.ptms.aide.Aide;
import ink.ptms.core.module.build.itemtool.util.Message;
import ink.ptms.core.module.build.itemtool.util.Utils;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.module.inject.TListener;
import io.izzel.taboolib.util.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.util.NumberConversions;

/**
 * @author 坏黑
 * @since 2018-10-14 13:16
 */
@TListener
public class CommandBlockState implements Listener {

    @TFunction.Init
    static void init() {
        if (!Aide.INSTANCE.getAllowItemTool()) {
            return;
        }
        CommandBuilder.create("setChestContents", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ChestContents.nonChest(((Player) sender).getItemInHand().getType())) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "ChestContents &8-> &6EDITING");
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        Inventory inventory = Bukkit.createInventory(new ChestContents(), 27, "Turn in Items");
                        BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                        assert itemMeta != null;
                        Chest blockState = (Chest) itemMeta.getBlockState();
                        inventory.setContents(blockState.getInventory().getContents());
                        ((Player) sender).openInventory(inventory);
                    }
                }).build();

        CommandBuilder.create("setSpawnerType", Aide.INSTANCE.getPlugin())
                .forceRegister()
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.SPAWNER) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Utils.INSTANCE.asEntityType(args[0]);
                        Message.INSTANCE.send(sender, "SpawnerType &8-> &f" + args[0].toUpperCase());
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                        assert itemMeta != null;
                        CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                        blockState.setSpawnedType(Utils.INSTANCE.asEntityType(args[0]));
                        itemMeta.setBlockState(blockState);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();

        CommandBuilder.create("setSpawnerDelay", Aide.INSTANCE.getPlugin())
                .permission("itemTool.use")
                .execute((sender, args) -> {
                    if (!(sender instanceof Player)) {
                        Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                    } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.SPAWNER) {
                        Message.INSTANCE.send(sender, "&cInvalid item.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (args.length == 0) {
                        Message.INSTANCE.send(sender, "&cInvalid arguments.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else if (NumberConversions.toInt(args[0]) < 0) {
                        Message.INSTANCE.send(sender, "&cInvalid delay number.");
                        Message.INSTANCE.getNO().play((Player) sender);
                    } else {
                        Message.INSTANCE.send(sender, "SpawnerDelay &8-> &f" + args[0]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                        // Action
                        BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                        assert itemMeta != null;
                        CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                        blockState.setDelay(NumberConversions.toInt(args[0]));
                        itemMeta.setBlockState(blockState);
                        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    }
                }).build();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof ChestContents) {
            if (ChestContents.nonChest(e.getPlayer().getItemInHand().getType())) {
                Message.INSTANCE.send(e.getPlayer(), "&cInvalid item.");
                Message.INSTANCE.getNO().play((Player) e.getPlayer());
            } else {
                Message.INSTANCE.send(e.getPlayer(), "ChestContents &8-> &aSAVED");
                Message.INSTANCE.getITEM_EDIT().play((Player) e.getPlayer());
                // Action
                BlockStateMeta itemMeta = (BlockStateMeta) e.getPlayer().getItemInHand().getItemMeta();
                assert itemMeta != null;
                Chest blockState = (Chest) itemMeta.getBlockState();
                blockState.getInventory().setContents(e.getInventory().getContents());
                itemMeta.setBlockState(blockState);
                e.getPlayer().getItemInHand().setItemMeta(itemMeta);
            }
        }
    }

    private static class ChestContents implements InventoryHolder {

        @Override
        public Inventory getInventory() {
            return null;
        }

        public static boolean nonChest(Material material) {
            return material != Material.CHEST && material != Material.TRAPPED_CHEST;
        }
    }
}
