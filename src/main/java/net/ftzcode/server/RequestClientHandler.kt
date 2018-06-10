package net.ftzcode.server

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.util.CharsetUtil
import net.ftzcode.util.AppConf
import org.slf4j.LoggerFactory

/**
 *  Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * 处理客户端请求本服务器的管理者
 */

class RequestClientHandler : SimpleChannelInboundHandler<FullHttpRequest>() {

    private val logger = LoggerFactory.getLogger("RequestClientHandler")

    override fun channelActive(ctx: ChannelHandlerContext?) {
        // println("客户端请求通道激活...")
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpRequest?) {
        val remotePort = AppConf.getStringValue()["remotePort"]!!.toInt()
        val remoteHost = AppConf.getStringValue()["remoteHost"]


        //获取客户端请求的通道
        var inboundChannel = ctx!!.channel()

        var url = msg!!.uri()
        var method = msg.method().name()
        var httpVersion = msg.protocolVersion().text()
        var responseContent = msg!!.content().toString(CharsetUtil.UTF_8)


        val sb = StringBuilder(500)
        sb.append("\n 正在读取客户端请求数据... \nHeaderInfo：\n")
        msg.headers().forEach {
            msg.headers().set("Host", remoteHost)
            sb.append("${it.key}  : ${it.value}  \n")
        }
        msg.headers().set(HttpHeaderNames.ACCEPT, "*/*")
        sb.append("RequestData：$responseContent \n")
        sb.append("TargetUrl：$url， Method：$method，HttpVersion：$httpVersion \n\n")
        logger.info(sb.toString())

        val b = Bootstrap()
        b.group(inboundChannel.eventLoop())
                .channel(inboundChannel.javaClass)
                .handler(ResponseHandlerInitializer(inboundChannel, remotePort == 443))
        val f = b.connect(remoteHost, remotePort)
        //获取客户端请求远程服务器通道
        var outboundChannel = f.channel()
        msg!!.retain()
        //操作完成回调， 将客户端浏览器的请求数据写入到远程服务器
        f.addListener({ future ->
            if (future.isSuccess) {
                //客户端请求远程服务器通道   出站消息  请求远程服务器,将request请求写入进去
                outboundChannel.writeAndFlush(msg)
            } else {
                logger.error("对不起，远程服务连接失败，请检查 $remoteHost,$remotePort 是否正确！ ")
                inboundChannel.close()
            }
        })
    }


    override fun channelInactive(ctx: ChannelHandlerContext?) {
        // println("客户端请求通道已经失效...")
        ctx!!.channel().close()
    }


}