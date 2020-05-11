package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperWatcher implements Watcher {

    private static ZooKeeper zk;
    private static Stat stat = new Stat();

    // 中间代码略

    /**
     * 监听事件
     */
    @Override
    public void process(WatchedEvent event) {
        // zk连接成功通知事件
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                System.out.println("zookeeper连接成功");
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
