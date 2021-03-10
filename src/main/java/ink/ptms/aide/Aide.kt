package ink.ptms.aide

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject

/**
 * @author 坏黑
 * @since 2019-07-14 18:29
 */
object Aide : Plugin() {

    @TInject
    lateinit var conf: TConfig
        private set

    val allowItemTool: Boolean
        get() = conf.getBoolean("item-tool")

    override fun allowHotswap() = false
}