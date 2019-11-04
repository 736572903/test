package zookeeper;

import org.apache.zookeeper.AsyncCallback;

/**
 * 功能描述: 该类用于节点异步创建回调
 *
 * @author wanghouji
 * @date 2019/11/4 2:24 下午
 */
public class StringCallBackTest implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object object, String name) {
        // Create path result: [0, /javatestasync, 传递数据, real path name: /javatestasync
        System.out.println("异步创建节点完成 result: [" + rc + ", " + path + ", " + object + ", real path name: " + name);
    }
}
