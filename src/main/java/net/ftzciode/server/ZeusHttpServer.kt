package net.ftzciode.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import net.ftzciode.util.AppConf

/**
 *  Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 */

class ZeusHttpServer {

    @Throws(Exception::class)
    fun start() {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        val port=AppConf.getStringValue()["localPort"]!!.toInt()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    // 前端handler  用于接收浏览器客户端的请求处理
                    .childHandler(RequestHandlerInitializer())
                    //服务器绑定到这个端口
                    .bind(port).sync().channel().closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }

    }

}