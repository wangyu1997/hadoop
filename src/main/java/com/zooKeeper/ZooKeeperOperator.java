package com.zooKeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZooKeeperOperator extends AbstractZooKeeper {
    private Log log = LogFactory.getLog(ZooKeeperOperator.class.getName());

    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        Stat exists = this.pathExists(path);
        if (exists == null) {
            this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(path + " 创建成功!");
        }
    }

    public Stat pathExists(String path) throws KeeperException, InterruptedException {
        Stat stat = null;
        stat = zooKeeper.exists(path, false);
        return stat;
    }

    public void getChild(String path) throws KeeperException, InterruptedException {
        List<String> list = this.zooKeeper.getChildren(path, false);
        if (list.isEmpty()) {
            System.out.println(path + " 中没有节点");
        } else {
            System.out.println(path + " 中节点为:");
            list.forEach(System.out::println);
        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

    public void deletePath(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
        System.out.println(path + " 删除成功");
    }

    public static void main(String[] args) {
        ZooKeeperOperator operator = new ZooKeeperOperator();
        try {
            operator.connect("10.13.119.181:2181");
//            operator.create("/test.txt", "helllodsdssdsds".getBytes());
            operator.getChild("/test");
            operator.deletePath("/test/b");
            operator.getData("/test");
            operator.getChild("/");
        } catch (KeeperException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

