package com.socket;

import com.sun.xml.internal.ws.api.message.Attachment;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import scala.Char;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOSokcetServer {
    //通道管理器
    private Selector selector;

    //获取一个ServerSocket通道，并初始化通道
    public NIOSokcetServer init(int port) throws IOException {
        //获取一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        //获取通道管理器
        selector=Selector.open();
        //将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
        //只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        return this;
    }

    public void listen() throws IOException{
        System.out.println("服务器端启动成功");

        //使用轮询访问selector
        while(true){
            //当有注册的事件到达时，方法返回，否则阻塞。
            selector.select();
            System.out.println("服务端开始轮询");
            //获取selector中的迭代器，选中项为注册的事件
            Iterator<SelectionKey> ite=selector.selectedKeys().iterator();
            System.out.println("selector.selectedKeys:" + selector.selectedKeys());
            while(ite.hasNext()){
                SelectionKey key = ite.next();
                //删除已选key，防止重复处理
                ite.remove();
                //客户端请求连接事件
                if(key.isAcceptable()){
                    System.out.println("isAcceptable");
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    System.out.println("serverSocketChannel :" + server);
                    //获得客户端连接通道
                    SocketChannel channel = server.accept();

                    channel.configureBlocking(false);
                    //向客户端发消息
//                    channel.write(ByteBuffer.wrap(new String("send message to client").getBytes()));
                    //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.putChar('p');
                    channel.register(selector, SelectionKey.OP_READ,byteBuffer);

                    System.out.println("clientSocketChannel : " + channel);
                    System.out.println("客户端请求连接事件");
                }else if(key.isReadable()){//有可读数据事件
                    System.out.println("isReadable");
                    //获取客户端传输数据可读取消息通道。
                    SocketChannel channel = (SocketChannel)key.channel();
                    System.out.println("clientChannel : " + channel);
                    //创建读取数据缓冲器
//                    ByteBuffer buffer = ByteBuffer.allocate(10);
//                    int read = channel.read(buffer);
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    channel.read(byteBuffer) ;

                    byte[] data = byteBuffer.array();
                    String message = new String(data);
                    System.out.println("receive message from client, size:" + byteBuffer.position() + " msg: " + message);
//                    ByteBuffer outbuffer = ByteBuffer.wrap(("server.".concat(msg)).getBytes());
//                    channel.write(outbuffer);
                    key.interestOps(SelectionKey.OP_WRITE);
                }else if(key.isWritable()) {

//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    //在原有的byteBuffer基础上添加新的数据，将会附加到缓冲区的尾部
                    byteBuffer.putChar('r');
                    byteBuffer.putChar('r');
                    byteBuffer.putChar('r');

                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    System.out.println("isWritable socketChannle :" + socketChannel);
                    //获取缓冲区的索引为0的数据,结果为 'p'
                    char c = byteBuffer.getChar(0);
                    System.out.println("char :" + c);
                    byte[] bytes = byteBuffer.array();
                    String byteStr = new String(bytes) ;
                    System.out.println("byteStr :" + byteStr);
                    System.out.println("byteStr.position : " + byteBuffer.position());
                    byteBuffer.position(20) ;
//                    socketChannel.write(ByteBuffer.wrap(new String("response to client").getBytes()));
//                    int serverWriteCount = socketChannel.write(ByteBuffer.wrap(byteBuffer.array()));

                    /**
                     * SocketChannel的write(ByteBuffer byteBuffer)方法是将ByteBuffer对象position之后的字节数据写到socketChannel上
                     * 而socketChannel.write(ByteBuffer.wrap(bytes)) ;是将ByteBuffer对象position之前的字节数据写入到socketChannel上
                     * rite(ByteBuffer[] srcs, int offset, int length) 从给定的偏移量开始之后的length个字节的数据写入到socketChannel上。
                     */
                    socketChannel.write(byteBuffer) ;
//                    System.out.println("服务端写向客户端的字节数为： " + serverWriteCount);
                    key.interestOps(SelectionKey.OP_READ) ;
                }
            }
            System.out.println("--------------------------------------------");
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOSokcetServer().init(9981).listen();
    }
}
