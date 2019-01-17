package net.ftzcode.server

import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.DefaultFullHttpResponse
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.HttpHeaderNames.*
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory
import java.lang.StringBuilder

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 */

class ResponseClientHandler(private var requestId: String, private var inboundChannel: Channel) : SimpleChannelInboundHandler<FullHttpResponse>() {

    private val logger = LoggerFactory.getLogger("ResponseClientHandler")


    override fun channelActive(ctx: ChannelHandlerContext?) {
        // println("客户端请求远程服务端通道激活...")
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpResponse?) {
        // println("客户端请求远程服务端通道正在读取服务器返回的数据...")
        /***
        ByteBuf使用了引用计数，缺省下读取一次之后refCnt就会减到0，再读就出现异常了。
        如果使用retain()可以增加引用计数，可以多读一次；相反，release()则减一次，可能还没有读就不能再读了。
         */

        //允许跨域访问
        msg!!.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        msg.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept")
        msg.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT,DELETE")

        //客户端请求本服务，本服务请求的远程服务返回的数据

        //val contentType = msg.headers()["Content-Type"]
        val response = msg.content().toString(CharsetUtil.UTF_8)
        val sb = StringBuilder()
        sb.append("\n ResponseData:")
                .append("\n requestId:$requestId")
                .append("\n $response \n")
        logger.info(sb.toString())


        // 将请求远程服务器返回的数据写入到浏览器客户端   inboundChannel是浏览器客户端的通道
        inboundChannel.writeAndFlush(msg.retain()).addListener(ChannelFutureListener { future ->
            if (!future.isSuccess) {
                future.channel().close()
            }
        })

    }


    override fun channelInactive(ctx: ChannelHandlerContext?) {
        // println("客户端请求远程服务端通道已经失效...")
        ctx!!.channel().close()
    }


}