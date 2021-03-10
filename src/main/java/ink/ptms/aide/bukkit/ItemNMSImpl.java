package ink.ptms.aide.bukkit;

import ink.ptms.core.module.build.itemtool.util.Message;
import io.izzel.taboolib.Version;
import io.izzel.taboolib.util.Reflection;
import io.izzel.taboolib.util.lite.Numbers;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:49
 */
@SuppressWarnings("ALL")
public class ItemNMSImpl extends ItemNMS {

    private Field tagListField;
    private Field intArrayDataField;
    private Field byteArrayDataField;
    private Field longArrayDataField;

    public ItemNMSImpl() {
        try {
            tagListField = Reflection.getField(NBTTagList.class, true, "list");
            intArrayDataField = Reflection.getField(NBTTagIntArray.class, true, "data");
            byteArrayDataField = Reflection.getField(NBTTagByteArray.class, true, "data");
            // v1.12+
            if (Version.isAfter(Version.v1_13)) {
                longArrayDataField = NBTTagLongArray.class.getDeclaredFields()[0];
                longArrayDataField.setAccessible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack addAttribute(ItemStack itemStack, String attribute, String value, String equipment) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        Object attributeCompound = new NBTTagCompound();
        Object attributeModifiers = ((NBTTagCompound) itemTag).hasKey("AttributeModifiers") ? ((NBTTagCompound) itemTag).getList("AttributeModifiers", 10) : new NBTTagList();
        ((NBTTagCompound) attributeCompound).setString("Name", attribute);
        ((NBTTagCompound) attributeCompound).setString("AttributeName", attribute);
        ((NBTTagCompound) attributeCompound).setInt("UUIDMost", Numbers.getRandom().nextInt(Integer.MAX_VALUE));
        ((NBTTagCompound) attributeCompound).setInt("UUIDLeast", Numbers.getRandom().nextInt(Integer.MAX_VALUE));
        if (value.endsWith("%")) {
            ((NBTTagCompound) attributeCompound).setInt("Operation", 1);
            ((NBTTagCompound) attributeCompound).setDouble("Amount", NumberConversions.toDouble(value.substring(0, value.length() - 1)) / 100.0D);
        } else {
            ((NBTTagCompound) attributeCompound).setInt("Operation", 0);
            ((NBTTagCompound) attributeCompound).setDouble("Amount", NumberConversions.toDouble(value));
        }
        if (!equipment.equalsIgnoreCase("all")) {
            ((NBTTagCompound) attributeCompound).setString("Slot", equipment);
        }
        ((NBTTagList) attributeModifiers).add(((NBTTagCompound) attributeCompound));
        ((NBTTagCompound) itemTag).set("AttributeModifiers", ((NBTTagList) attributeModifiers));
        ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_16_R1.ItemStack) nmsItem));
    }

    @Override
    public ItemStack removeAttribute(ItemStack itemStack, String attribute) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        Object attributeCompound = new NBTTagCompound();
        Object attributeModifiers = ((NBTTagCompound) itemTag).hasKey("AttributeModifiers") ? ((NBTTagCompound) itemTag).getList("AttributeModifiers", 10) : new NBTTagList();
        try {
            List list = new CopyOnWriteArrayList((List) tagListField.get(attributeModifiers));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof NBTTagCompound && ((NBTTagCompound) list.get(i)).hasKey("AttributeName") && ((NBTTagCompound) list.get(i)).getString("AttributeName").equalsIgnoreCase(attribute)) {
                    list.remove(i);
                }
            }
            tagListField.set(attributeModifiers, list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ((NBTTagCompound) itemTag).set("AttributeModifiers", ((NBTTagList) attributeModifiers));
        ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_16_R1.ItemStack) nmsItem));
    }

    @Override
    public ItemStack setItemTag(ItemStack itemStack, String key, String value) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        ((NBTTagCompound) itemTag).setString(key, value);
        ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_16_R1.ItemStack) nmsItem));
    }

    @Override
    public String getItemTag(ItemStack itemStack, String key) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        return ((NBTTagCompound) itemTag).getString(key);
    }

    @Override
    public ItemStack clearNBT(ItemStack itemStack) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).setTag(new NBTTagCompound());
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_16_R1.ItemStack) nmsItem));
    }

    @Override
    public void sendItemNBT(Player player, ItemStack itemStack) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_16_R1.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        sendItemNBT(player, "NBT &8->&f", itemTag, 0);
    }

    private void sendItemNBT(Player player, String key, Object nbtBase, int node) {
        if (nbtBase instanceof NBTTagCompound) {
            Set<String> keys = ((net.minecraft.server.v1_16_R1.NBTTagCompound) nbtBase).getKeys();
            if (keys.isEmpty()) {
                Message.INSTANCE.send(player, getNodeSpace(node) + key + " {}");
            } else {
                Message.INSTANCE.send(player, getNodeSpace(node) + key + (key.equals("-") ? " {" : ""));
                for (String subKey : keys) {
                    sendItemNBT(player, subKey + ":", ((NBTTagCompound) nbtBase).get(subKey), node + 1);
                }
                if (key.equals("-")) {
                    Message.INSTANCE.send(player, getNodeSpace(node) + "}");
                }
            }
        } else if (nbtBase instanceof NBTTagList) {
            try {
                List tagList = (List) tagListField.get(nbtBase);
                if (tagList.isEmpty()) {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key);
                    tagList.forEach(aTagList -> sendItemNBT(player, "-", (NBTBase) aTagList, node));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase instanceof NBTTagIntArray) {
            try {
                int[] array = (int[]) intArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key);
                    for (int var : array) {
                        Message.INSTANCE.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase instanceof NBTTagByteArray) {
            try {
                byte[] array = (byte[]) byteArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key);
                    for (byte var : array) {
                        Message.INSTANCE.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase.getClass().getSimpleName().equals("NBTTagLongArray")) {
            try {
                long[] array = (long[]) longArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.INSTANCE.send(player, getNodeSpace(node) + key);
                    for (long var : array) {
                        Message.INSTANCE.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            Message.INSTANCE.send(player, getNodeSpace(node) + key + " &f" + (nbtBase == null ? "" : nbtBase instanceof NBTTagString ? "&7\"&r" + nbtBase.toString().substring(1, nbtBase.toString().length() - 1) + "&7\"" : nbtBase.toString()));
        }
    }

    private String getNodeSpace(int node) {
        return IntStream.range(0, node).mapToObj(i -> "  ").collect(Collectors.joining());
    }
}
