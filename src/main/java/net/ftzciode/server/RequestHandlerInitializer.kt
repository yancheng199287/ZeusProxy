package net.ftzciode.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpContentCompressor
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec

/**
 * Created by WangZiHe on 2017/8/29.
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 */

class RequestHandlerInitializer : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        ch!!.pipeline()
                .addLast(HttpServerCodec())
                .addLast(HttpObjectAggregator(1024 * 1024 * 100))
                //只能服务器返回数据进行压缩 ，客户端不要设置   压缩级别1到9
                .addLast(HttpContentCompressor(1))
                .addLast(RequestClientHandler())
    }

}