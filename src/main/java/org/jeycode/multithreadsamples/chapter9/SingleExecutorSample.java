package org.jeycode.multithreadsamples.chapter9;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jeycode.multithreadsamples.helpers.OwnThreadFactory;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Single-Exec")
public final class SingleExecutorSample
{

      public static int MESSAGE_TAG = 0;
// @formatter:off
 
      public static void main(String[] args) throws InterruptedException
      {
            var singleThreadExecutor=newSingleThreadExecutor(new OwnThreadFactory());
          singleThreadExecutor.invokeAll(
                              IntStream.range(0,20)
                                       .mapToObj(num->(Callable<Integer>)SingleExecutorSample::genericTask)
                                       .collect(Collectors.toList())
                                     );
            
            singleThreadExecutor.shutdown();
            log.info("Resultado [{}] post ejecución",MESSAGE_TAG);
      }

      private static int genericTask()
      {
            log.info("------------------------\n");
            var currentThread = currentThread();
            var threadName = currentThread.getName();
            log.info("Ejecutamos la tarea en el thread: {}",threadName);

            log.info("TAG[{}]En el momento pre-actualización\n\t\t MrThread: {} ",MESSAGE_TAG,threadName);
            var timeout = 300;
            sleepMillis(timeout);
            return MESSAGE_TAG++;

      }

      private static void sleepMillis(int timeout)
      {
            try
            {
                  MILLISECONDS.sleep(timeout);
            }
            catch (InterruptedException ex)
            {}
      }

}
