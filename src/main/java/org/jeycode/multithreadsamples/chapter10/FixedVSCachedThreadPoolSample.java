package org.jeycode.multithreadsamples.chapter10;

import static java.lang.Thread.currentThread;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.jeycode.multithreadsamples.helpers.OwnThreadFactory;
import org.jeycode.multithreadsamples.helpers.Sleep;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Fixed-Cached-Exec")
public class FixedVSCachedThreadPoolSample
{

      private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime()
                                                             .availableProcessors();
      private static final Random RANDOM_OBJ = new Random();
      public static final AtomicInteger MESSAGE_TAG = new AtomicInteger(0);
      public static final Queue<String> USED_THREADS = new ConcurrentLinkedQueue<>();

      public static void main(String[] args)
      {
//            final ExecutorService threadExecutorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS,new OwnThreadFactory());
            final ExecutorService threadExecutorService = Executors.newCachedThreadPool(new OwnThreadFactory());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());
            threadExecutorService.submit(genericTask());

            threadExecutorService.shutdown();
            while (!threadExecutorService.isTerminated())
            {}
            log.info("Resultado post-ejecución: [{}]\n",MESSAGE_TAG.get());

            USED_THREADS.stream()
                        .sorted()
                        .forEach(System.out::println);
      }

      private static Callable<Integer> genericTask()
      {
            return ()->
                  {

                        var currentThread = currentThread();
                        var threadName = currentThread.getName();
                        Sleep.millis(RANDOM_OBJ.nextInt(1000));
                        var messageTagIncremented = MESSAGE_TAG.incrementAndGet();
                        USED_THREADS.add(threadName);
                        log.info("Tag: {} => Ejecutamos la tarea en el thread: {}\n",messageTagIncremented,threadName);
                        return messageTagIncremented;
                  };
      }

}
