package com.example.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * 分布式配置中心demo
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/13/10:20
 * @Description:
 */
@Component
public class ZooKeeperProSync implements Watcher {

    private static final int SESSION_TIMEOUT = 5000;
    protected static ZooKeeper zk = null;//客户端连接
    private CountDownLatch connectedSignal = new CountDownLatch(1);//闭锁

    /**
     * 客户端与ZK建立连接后，Watcher的process方法会被调用，参数是表示该连接的事件，
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        //事件类型
        Event.EventType type = event.getType();
        //事件状态  即  连接不连接
        Event.KeeperState state = event.getState();
        //事件路径
        String path = event.getPath();
        // 连接成功后调用CountDownLatch的countDown方法，计数器减为0，释放线程锁，zk对象可用
        if (state == Event.KeeperState.SyncConnected) {
            if (type.None == type) {
                System.out.println("连接类型");
                //连接上zk服务器后放开信号量
                connectedSignal.countDown();
                System.out.println("=====ZK连接成功=====");
            } else if (type == type.NodeCreated) {
                System.out.println("=====新增节点成功=====path:" + path);
            } else if (type == type.NodeDeleted) {
                System.out.println("=====删除节点成功=====path:" + path);
            } else if (type == type.NodeDataChanged) {
                System.out.println("=====修改节点成功=====path:" + path);
            } else {

            }
            System.out.println("事件通知开始后");
        }else  if(state== Event.KeeperState.Disconnected){
            System.out.println("客户端下线");
        }
    }

    public void connect(String hosts) throws IOException, InterruptedException {
        //第一个参数是Zookeeper服务主机地址，可指定端口号，默认为2181；第二个参数以毫秒为单位的会话超时参数；
        // 第三个参数是一个Watcher对象的实例。Watcher对象接收来自于Zookeeper的回调，以获得各种事件通知，
        // 本例中CreateGroup是一个Watcher对象，因此参数为this
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        //当一个ZooKeeper的实例被创建时，会启动一个线程连接到Zookeeper服务。
        // 由于对构造函数的调用是立即返回的，因此在使用新建的Zookeeper对象之前一定要等待其与Zookeeper服务之间的连接建立成功。
        // 使用CountDownLatch使当前线程等待，直到Zookeeper对象准备就绪
        connectedSignal.await();
    }

    /**
     * 断开连接
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        zk.close();
    }
}
