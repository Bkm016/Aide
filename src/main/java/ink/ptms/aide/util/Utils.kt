package ink.ptms.core.module.build.itemtool.util

import com.google.common.collect.Maps
import io.izzel.taboolib.util.lite.Numbers
import org.bukkit.Color
import org.bukkit.entity.EntityType
import org.bukkit.util.NumberConversions

/**
 * @Author 坏黑
 * @Since 2018-10-13 16:13
 */
object Utils {

    private val colors = Maps.newHashMap<String, Color>()

    init {
        colors["WHITE"] = Color.fromRGB(16777215)
        colors["SILVER"] = Color.fromRGB(12632256)
        colors["GRAY"] = Color.fromRGB(8421504)
        colors["BLACK"] = Color.fromRGB(0)
        colors["RED"] = Color.fromRGB(16711680)
        colors["MAROON"] = Color.fromRGB(8388608)
        colors["YELLOW"] = Color.fromRGB(16776960)
        colors["OLIVE"] = Color.fromRGB(8421376)
        colors["LIME"] = Color.fromRGB(65280)
        colors["GREEN"] = Color.fromRGB(32768)
        colors["AQUA"] = Color.fromRGB(65535)
        colors["TEAL"] = Color.fromRGB(32896)
        colors["BLUE"] = Color.fromRGB(255)
        colors["NAVY"] = Color.fromRGB(128)
        colors["FUCHSIA"] = Color.fromRGB(16711935)
        colors["PURPLE"] = Color.fromRGB(8388736)
        colors["ORANGE"] = Color.fromRGB(16753920)
    }

    fun asEntityType(name: String): EntityType {
        val entityType = EntityType.fromName(name)
        return entityType ?: EntityType.fromId(NumberConversions.toInt(name))!!
    }

    fun asColorArray(name: String): Array<Color> {
        val array = name.split("\\|".toRegex())
        return array.map { i -> asColor(i) }.toTypedArray()
    }

    fun randomColor(): Color {
        return Color.fromRGB(Numbers.getRandomInteger(0, 255), Numbers.getRandomInteger(0, 255), Numbers.getRandomInteger(0, 255))
    }

    fun asColor(name: String): Color {
        if (name.equals("random", ignoreCase = true) || name.equals("r", ignoreCase = true)) {
            return randomColor()
        }
        val color = colors[name.toUpperCase()]
        if (color != null) {
            return color
        }
        val split = name.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (split.size == 3) Color.fromRGB(NumberConversions.toInt(split[0]), NumberConversions.toInt(split[1]), NumberConversions.toInt(split[2])) else Color.fromRGB(NumberConversions.toInt(split[0]))
    }

    fun isNumber(obj: Any): Boolean {
        if (obj is Number) {
            return true
        }
        try {
            java.lang.Double.valueOf(obj.toString())
            return true
        } catch (ignored: Exception) {
        }
        return false
    }

    fun isBoolean(obj: Any): Boolean {
        if (obj is Number) {
            return true
        }
        try {
            java.lang.Boolean.valueOf(obj.toString())
            return true
        } catch (ignored: Exception) {
        }
        return false
    }
}
