package net.ftzcode

import net.ftzcode.server.ZeusHttpServer
import net.ftzcode.util.AppConf

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:http:www.520code.net
 * Github:https://github.com/yancheng199287
 */


fun main(args: Array<String>) {
    println("-----------------------------------------------------------\n")
    println("Welcome to use ZeusProxy！ Power By YC @ www.520code.net")
    println("Project: https://github.com/yancheng199287/ZeusProxy ")
    println("\n-----------------------------------------------------------\n")

    println("current path: "+AppConf.root)
    println("Start Service...  binding ${AppConf.getStringValue()["localPort"]} port")
    ZeusHttpServer().start()
}