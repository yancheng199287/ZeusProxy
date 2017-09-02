package net.ftzciode.server

import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 */

class ResponseClientHandler(private var inboundChannel: Channel) : SimpleChannelInboundHandler<FullHttpResponse>() {

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

        //客户端请求本服务，本服务请求的远程服务返回的数据
        var response = msg!!.content().toString(CharsetUtil.UTF_8)

        logger.info("\n ResponseData:\n" + response)


        // 将请求远程服务器返回的数据写入到浏览器客户端   inboundChannel是浏览器客户端的通道
        inboundChannel.writeAndFlush(msg!!.retain()).addListener(ChannelFutureListener { future ->
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