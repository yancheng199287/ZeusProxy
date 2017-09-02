package net.ftzciode

import net.ftzciode.server.ZeusHttpServer
import net.ftzciode.util.AppConf

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:http:www.520code.net
 * Github:https://github.com/yancheng199287
 */


fun main(args: Array<String>) {
    println("Welcome to use ZeusProxy！")
    println("Start Service...  binding ${AppConf.getStringValue()["localPort"]} port")
    AppConf.getStringValue()
    ZeusHttpServer().start()
}