package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 功能描述:学习zookeeper
 *
 * @author wanghouji
 * @date 2019/11/4 10:59 上午
 */
public class ZookeeperTest implements Watcher {

    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        // zookeeper配置数据存放路径
        String path = "/javatest";
        // 连接zookeeper并且注册一个默认的监听器
        zk = new ZooKeeper("10.41.32.133:2181", 5000, new ZookeeperTest());
        // 等待zk连接成功的通知
        connectedSemaphore.await();

        Stat stat = zk.exists(path, new ZookeeperTest());
        System.out.println("查询该路径是否创建: " + (stat == null ? "未创建" : stat.toString()));

        stat = stat == null ? new Stat() : stat;

        // https://blog.csdn.net/en_joker/article/details/78688140,创建节点API调用参考

        /**
         * 功能描述:同步创建节点
         * String path,需要创建的数据节点的节点路径
         * byte[] data,一个字节数组，是节点创建后的初始内容
         * List<ACL> acl,节点的ACL策略
         * CreateMode createMode：
         * 节点类型，是一个枚举类型，通常有4种可选的节点类型
         *     持久（PERSISTENT）
         *     持久顺序（PERSISTENT_SEQUENTIAL）
         *     临时（EPHEMERAL）
         *     临时顺序（EPHEMERAL_SEQUENTIAL）
         *
         */
        String result = zk.create(path, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 异步创建 + 回调通知
        zk.create(path + "async", "test".getBytes(),  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new StringCallBackTest(), "传递数据");

        // 创建节点返回值：/javatest
        System.out.println("创建节点的路径：" + result);

        // 获取path目录节点的配置数据，并注册默认的监听器
        System.out.println("获取节点的数据" + new String(zk.getData(path, true, stat)));

        // 设置新值
        zk.setData(path, "newTest".getBytes(), stat.getVersion());

        // 删除节点
        zk.delete(path, stat.getVersion() + 1);

        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
        // zk连接成功通知事件
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                System.out.println("zookeeper连接成功");
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeCreated) {
                // zk目录节点创建
                try {
                    // 节点已经创建，路径为：/javatest
                    System.out.println("节点已经创建，路径为：" + event.getPath());
                } catch (Exception e) {
                }
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                // zk目录节点数据变化通知事件
                try {
                    // 配置已修改，新值为：newData
                    System.out.println("配置已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
                } catch (Exception e) {
                }
            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                // zk目录节点的子节点变化通知事件
                try {
                    System.out.println("子节点已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.getType() == Event.EventType.NodeDeleted) {
                // zk目录节点删除
                try {
                    // 节点已经删除，路径为：/javatest
                    System.out.println("节点已经删除，路径为：" + event.getPath());
                } catch (Exception e) {
                }
            }
        }
    }
}
