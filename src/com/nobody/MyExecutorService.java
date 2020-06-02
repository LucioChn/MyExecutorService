package com.nobody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义线程池
 * 本实现没考虑空闲线程处理，拒绝策略等，后续再将这部分补上。
 * 有兴趣者可以自行实现下。
 * 
 * @author Μr.ηobοdy
 *
 * @date 2020-06-02
 *
 */
public class MyExecutorService {

    // 一直保持运行的线程
    private List<WorkThread> workThreads;

    // 存放未执行任务的队列容器
    private final BlockingQueue<Runnable> workQueue;

    // 标志线程池关闭与否 corePoolSize queueCapacity
    private boolean isWorking = true;

    // 构造函数，初始化线程池
    public MyExecutorService(int corePoolSize, int queueCapacity) {
        workThreads = new ArrayList<>(corePoolSize);
        workQueue = new LinkedBlockingQueue<>(queueCapacity);
        // 提前预热创建固定数量的线程，保持运行状态
        for (int i = 0; i < corePoolSize; i++) {
            WorkThread workThread = new WorkThread();
            workThread.start();
            workThreads.add(workThread);
        }
    }

    // 调用execute执行任务，实际上先放入任务队列中
    public boolean execute(Runnable task) {
        if (null != task) {
            return workQueue.offer(task);
        }
        return false;
    }

    // 优雅关闭线程池
    public void shutdown() {
        isWorking = false;
    }

    // 工作线程类
    class WorkThread extends Thread {
        @Override
        public void run() {
            // 如果线程池未关闭，并且任务队列还有未执行任务，则线程不断从任务队列取任务，如果能取到则执行任务的run方法
            while (isWorking || !workQueue.isEmpty()) {
                Runnable task = workQueue.poll();
                if (null != task) {
                    task.run();
                }
            }
        }
    }

}
