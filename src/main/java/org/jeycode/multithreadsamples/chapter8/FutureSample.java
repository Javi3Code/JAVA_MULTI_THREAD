package org.jeycode.multithreadsamples.chapter8;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureSample
{

      public static void main(String[] args) throws InterruptedException,ExecutionException,TimeoutException
      {
            var pool = ForkJoinPool.commonPool();
            var newSingleExecutorService = newSingleThreadExecutor();
            FutureTask<String> future = new FutureTask<>(FutureSample::task);
            pool.execute(future);
//            var result = future.get();
//            System.out.println(result);
            var future2 = newSingleExecutorService.submit(FutureSample::task);
            newSingleExecutorService.shutdown();
      }

      private static String task()
      {

            try
            {
                  var currentThread = currentThread();
                  boolean daemon = currentThread.isDaemon();
                  if (daemon)
                  {
                        sleep(1000);
                  }
                  else
                  {
                        sleep(3000);
                  }
                  System.out.printf("Hilo[%s] -- isDaemon: %s \n",currentThread.getName(),daemon);

            }
            catch (Exception ex)
            {}
            return "completada task";
      }

}
