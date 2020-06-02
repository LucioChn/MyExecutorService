package com.nobody;

/**
 * 测试线程池
 * 
 * @author Μr.ηobοdy
 *
 * @date 2020-06-02
 *
 */
public class Main {

    public static void main(String[] args) {

        // 1.创建线程池
        MyExecutorService myExecutorService = new MyExecutorService(5, 100);

        // 2.创建15个任务
        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            myExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " 执行... i=" + finalI);
                }
            });
        }

        // 3.关闭线程池
        myExecutorService.shutdown();

    }

}
