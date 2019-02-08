import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Funn on 2018/9/3.
 */
public class DeadLockSample implements Runnable {

    private Object obj1;
    private Object obj2;

    public DeadLockSample(Object lock1, Object lock2) {
        obj1 = lock1;
        obj2 = lock2;
    }

    public void run() {
        synchronized (obj1) {
            try {
                System.out.println(Thread.currentThread() + " obtain obj1");
                Thread.sleep(1000L);
                synchronized (obj2) {
                    System.out.println(Thread.currentThread() + " obtain obj2");
                    Thread.sleep(1000L);
                }
            } catch (Exception ex) {

            }
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = new Runnable() {
            @Override
            public void run() {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds != null) {
                    ThreadInfo[] threadInfos = mxBean.getThreadInfo(threadIds);
                    System.out.println("Detected deadlock threads: ");
                    for (ThreadInfo threadInfo : threadInfos) {
                        System.out.println(threadInfo.getThreadName() + threadInfo.getThreadState());
                    }
                }
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);

        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread t1 = new Thread(new DeadLockSample(lock1, lock2));
        Thread t2 = new Thread(new DeadLockSample(lock2, lock1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }


}
