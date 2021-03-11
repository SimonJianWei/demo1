package com.example.util;

import com.example.config.ZooKeeperProSync;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/13/11:37
 * @Description:
 */
@Component
public class ZKOperateAPI extends ZooKeeperProSync {

    public void create(String goupName,String data) throws KeeperException, InterruptedException {
        String path = "/" + goupName;
        //创建znode节点，第一个参数为路径；第二个参数为znode内容，字节数组；
        // 第三个参数访问控制列表（简称ACL，此处使用完全开放的ACL，允许任何客户端对znode进行读写）；
        // 第四个为创建znode类型，此处是持久的（两种类型，短暂的和持久的，短暂类型会在客户端与zk服务断开连接后，被zk服务删掉，而持久的不会）
        String createPath = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created " + createPath);
    }

    /**
     * ZooDefs.Ids{
     * OPEN_ACL_UNSAFE  : 完全开放的ACL，任何连接的客户端都可以操作该属性znode
     * CREATOR_ALL_ACL : 只有创建者才有ACL权限
     * READ_ACL_UNSAFE：只能读取ACL
     * }
     *   CreateMode{
     *     PERSISTENT 永久
     *     PERSISTENT_SEQUENTIAL  持久顺序化
     *     EPHEMERAL   短暂有效 session结束后消失
     * }
     * @param groupName
     * @param memberName
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    //加入组
    public void join(String groupName, String memberName, String data) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        //创建短暂znode，会在客户端断开连接后删掉
        String createPath = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE  , CreateMode.PERSISTENT);
    }

    //列出组成员
    public void list(String groupName) {
        String path = "/" + groupName;
        try {
            //第一个参数为组名，即znode路径；第二个参数是否设置观察标识，如果为true，那么一旦znode状态改变，当前对象的Watcher会被触发
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.printf("No members in group %s\n", groupName);
                System.exit(1);
            }
            for (String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException ex) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    //删除组
    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            for (String child : children) {
                //删除方法第一个参数指定路径，第二个参数是版本号；这是一种乐观锁机制，如果指定的版本号和对应znode版本号一致才可删除.－1的话直接删除，无视版本；
                // 如果设置为-1，不校验可直接删除  其实就是默认最新版本
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException ex) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    /**
     * 修改数据
     * @param path 路径
     * @param data  数据内容
     * @param version  版本号
     */
    public void setData(String  path,String data,int version) throws  Exception {
        String grouPath="/"+path;
        zk.setData(grouPath,data.getBytes("utf-8"),-1);
        System.out.println("修改数据成功");

//        zk.getD
    }

}