package com.example.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ClientCurator
 * @Author Simon
 * @Date 2021/1/5 9:11
 * @Description zookeeper客户端curator
 */
@Component
public class ClientCurator {
    private static String host = "192.168.1.124:8081";
    private static int sessionTimeOut = 5000;
    private static int connectTimeOUt = 3000;
    public static CuratorFramework client;

    //创建客户端对象
    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // CuratorFramework client = CuratorFrameworkFactory.newClient(host, sessionTimeOut, connectTimeOUt, retryPolicy);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(host).
                sessionTimeoutMs(sessionTimeOut).
                retryPolicy(retryPolicy).build();
        client.start();
        return client;
    }

    public CuratorFramework init() {
        if (Objects.isNull(client)) {
            synchronized (this) {
                client = ClientCurator.getClient();
            }
        }
        return client;
    }

    //创建单个持久节点
    public void createSinglePersist(String path, String data) {
        try {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //递归创建永久节点
    public void createRecursionPersist(String path, String data) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跟新数据
    public void setData(String path, String data, String test) {

        try {
            client.setData().inBackground(new BackgroundCallback() {
                //异步回调接口
                @Override
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    int res = event.getResultCode();
                    String des;//描述相应码状态
                    switch (res) {
                        case 0:
                            des = "调用成功";
                            break;
                        case -4:
                            des = "客户端与服务器链接断开";
                            break;
                        case -110:
                            des = "节点不存在";
                            break;
                        case -112:
                            des = "会话过期";
                            break;
                        default:
                            des = "其他异常，暂不赘述";
                    }
                    System.out.println(des);
                    //事件类型
                    CuratorEventType type = event.getType();
                    if (type == CuratorEventType.SET_DATA) {
                        System.out.println("更新数据");
                    } else if (type == CuratorEventType.CREATE) {
                        System.out.println("创建节点");
                    }
                    System.out.println("上下文参数" + test);
                }
            }, test).forPath(path, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除单节点数据
    public void delete(String path) {
        try {
            client.delete().guaranteed().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据
    public Map getData(String path) {
        String data = new String();
        Stat stat = new Stat();
        Map map = new HashMap<String, Object>();
        try {
            data = client.getData().storingStatIn(stat).forPath(path).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("stat", stat);
        map.put("data", data);
        return map;
    }
}
