package ink.ptms.aide.bukkit

import io.izzel.taboolib.internal.xseries.XMaterial
import io.izzel.taboolib.module.tellraw.TellrawJson
import io.izzel.taboolib.util.item.ItemBuilder
import io.izzel.taboolib.util.item.Items
import io.izzel.taboolib.util.item.inventory.ClickEvent
import io.izzel.taboolib.util.item.inventory.ClickType
import io.izzel.taboolib.util.item.inventory.linked.MenuLinked
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull

/**
 * TabooLibDemo
 * io.izzel.taboolib.demo.SoundTest
 *
 * @author sky
 * @since 2021/3/1 12:19 上午
 */
class Sounds(player: Player) : MenuLinked<Sound>(player) {

    init {
        addButtonPreviousPage(47)
        addButtonNextPage(51)
    }

    override fun getTitle(): String {
        return "Sounds [Pg.$page]"
    }

    override fun getRows(): Int {
        return 6
    }

    override fun getElements(): MutableList<Sound> {
        return Sound.values().toMutableList()
    }

    override fun getSlots(): MutableList<Int> {
        return Items.INVENTORY_CENTER.toMutableList()
    }

    override fun onBuild(inventory: Inventory) {
        if (hasPreviousPage()) {
            inventory.setItem(47, ItemBuilder(XMaterial.ARROW).name("§7Previous").build())
        }
        if (hasNextPage()) {
            inventory.setItem(51, ItemBuilder(XMaterial.ARROW).name("§7Next").build())
        }
    }

    override fun onClick(event: ClickEvent, sound: Sound) {
        if (event.clickType == ClickType.CLICK) {
            val e = event.castClick()
            when (e.click) {
                org.bukkit.event.inventory.ClickType.LEFT -> {
                    event.clicker.playSound(event.clicker.location, sound, 1f, 0f)
                }
                org.bukkit.event.inventory.ClickType.MIDDLE, org.bukkit.event.inventory.ClickType.CREATIVE -> {
                    event.clicker.playSound(event.clicker.location, sound, 1f, 1f)
                }
                org.bukkit.event.inventory.ClickType.RIGHT -> {
                    event.clicker.playSound(event.clicker.location, sound, 1f, 2f)
                }
                org.bukkit.event.inventory.ClickType.SHIFT_RIGHT -> {
                    TellrawJson.create().append("§9§l§n${sound.name}")
                        .hoverText("Click To Copy!")
                        .clickSuggest(sound.name)
                        .send(event.clicker)
                }
                else -> {
                }
            }
        }
    }

    override fun generateItem(player: Player, sound: Sound, index: Int, slot: Int): ItemStack {
        return ItemBuilder(XMaterial.PAPER)
            .name("&f${sound.name}")
            .lore("", "&a[LEFT] [MIDDLE] [RIGHT]", "&6[SHIFT RIGHT]")
            .colored()
            .build()
    }
}