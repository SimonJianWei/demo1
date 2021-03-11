package com.example.service;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/10/17/14:15
 * @Description: socket 实践
 */
public class Server {

    public static void main(String[] args) throws IOException {
        //创建绑定到特定端口的服务器socket
        InetAddress address = InetAddress.getLocalHost();
        //向操作系统注册改服务端口
        ServerSocket server = new ServerSocket(8888, 50, address);
        StringBuffer mess = new StringBuffer();
        //持续处理客户端请求
        while (true) {
            Socket requestSocket = server.accept();
            try {
                InetAddress inetAddress = requestSocket.getInetAddress();
                byte[] address1 = inetAddress.getAddress();
                System.out.println(address.toString());
                BufferedReader in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream(),"utf-8"));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(requestSocket.getOutputStream(),"utf-8")),true);
                String info = "";
                while ((info = in.readLine()) != null) {
                    mess.append(info);
                }
                System.out.println("该实例" + address1 + " 的消息: " + mess);
                mess.setLength(0);//清空实例消息
                out.print("处理完本次请求");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                System.out.println("本次实例连接关闭");
                requestSocket.close();
            }
        }

    }
}
