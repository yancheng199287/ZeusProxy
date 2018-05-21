package net.ftzcode.util

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 *  Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 */

object AppConf {

    private val map = HashMap<String, String>(5)
    val root = System.getProperty("user.dir")

    fun getStringValue(): HashMap<String, String> {
        val appConfigPath = root + File.separator + "appConf.properties"
        if (map.size <= 0) {
            val file = File(appConfigPath)
            if (!file.exists()) {
                throw RuntimeException("请在此路径 $root 下指定一个配置文件appConf.properties")
            }
            val inputStream = BufferedInputStream(FileInputStream(file))
            val resourceBundle = PropertyResourceBundle(inputStream)
            // val resourceBundle = ResourceBundle.getBundle("appConf")
            resourceBundle.keySet().forEach {
                run {
                    var value = resourceBundle.getString(it)
                    map.put(it, value)
                    println("current config params：key:$it , value:$value")
                }
            }
        }
        return map
    }

}