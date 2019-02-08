import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Funn on 2018/9/18.
 */
public class StampedLockSample {

    private final StampedLock sl = new StampedLock();

//    void mutate() {
//        long stamp = sl.writeLock();
//        try {
//            write();
//        } finally {
//            sl.unlockWrite(stamp);
//        }
//    }
//
//    Thread.State
//
//    Data access() {
//        long stamp = sl.tryOptimisticRead();
//        Data data = read();
//        if (!sl.validate(stamp)) {
//            stamp = sl.readLock();
//            try {
//                data = read();
//            } finally {
//                sl.unlockRead(stamp);
//            }
//        }
//        return data;
//    }

    private void test() throws ExecutionException, InterruptedException {
        Runnable task = () -> {System.out.println("Hello World!");};
        Future future = Executors.newCachedThreadPool().submit(task);
        Executors.newCachedThreadPool().execute(task);
    }

    // ...
}
