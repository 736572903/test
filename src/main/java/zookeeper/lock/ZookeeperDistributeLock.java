package zookeeper.lock;

public class ZookeeperDistributeLock extends ZookeeperAbstractLock{
    @Override
    boolean trylock() {
        return false;
    }

    @Override
    void waitLock() {

    }
}
