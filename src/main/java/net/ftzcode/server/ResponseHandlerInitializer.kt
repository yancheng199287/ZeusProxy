package net.ftzcode.server

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpClientCodec
import io.netty.handler.codec.http.HttpObjectAggregator

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * 处理客户端返回响应给客户端的初始化管理者
 */
class ResponseHandlerInitializer(var inboundChannel: Channel) : ChannelInitializer<SocketChannel>(){

    override fun initChannel(ch: SocketChannel?) {
        ch!!.pipeline().addLast(HttpClientCodec())
                .addLast(HttpObjectAggregator(1024*1024*100))
                //注意客户端请求数据不要压缩数据，否则请求失败.addLast(HttpContentCompressor(1))
                .addLast(ResponseClientHandler(inboundChannel))
    }

}