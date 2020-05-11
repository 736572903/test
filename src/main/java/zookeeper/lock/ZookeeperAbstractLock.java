package zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock implements Lock {
    // zk连接地址
    private static final String CONNECTSTRING = "10.41.32.133:2181";
    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);
    // PATH
    protected static final String PATH = "/lock";

    /**
     * 获取锁
     */
    @Override
    public void getLock() {
        if (trylock()) {
            System.out.println("###获取lock锁当资源###");
        } else {
            //等待锁
            waitLock();
            //重新获取锁
            getLock();
        }
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        if (zkClient != null) {
            zkClient.delete(PATH);
            zkClient.close();
            System.out.println("###释放所资源###");
        }
    }

    /**
     * 判断是否获取锁成功，成功返回true，失败返回false
     */
    abstract boolean trylock();

    /**
     * 等待锁
     */
    abstract void waitLock();
}
