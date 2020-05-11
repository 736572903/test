package zookeeper.lock;

/**
 * 功能描述: 
 *
 * @author wanghouji
 * @date 2019/11/25 2:32 下午
 */
public interface Lock {
    /**
     * 功能描述: 获取锁
     *
     * @param
     * @return void
     * @author wanghouji
     * @date 2019/11/25 2:31 下午
     */
    public void getLock();

    /**
     * 功能描述: 释放锁
     *
     * @param
     * @return void
     * @author wanghouji
     * @date 2019/11/25 2:32 下午
     */
    public void unlock();
}
