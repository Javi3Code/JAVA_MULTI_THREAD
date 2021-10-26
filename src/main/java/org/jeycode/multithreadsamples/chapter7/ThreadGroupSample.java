package org.jeycode.multithreadsamples.chapter7;

import static java.lang.Thread.*;

import java.util.List;

public class ThreadGroupSample
{

      public static void main(String[] args) throws InterruptedException
      {
            ThreadGroup threadGroup = new ThreadGroup("[Jey-Code-group]");
            var threadOne = new Thread(threadGroup,()-> heavyTask(500),"Thread-500");
            var threadTwo = new Thread(threadGroup,()-> heavyTask(1000),"Thread-1000");
            var threadThree = new Thread(threadGroup,()-> heavyTask(2000),"Thread-2000");
            threadGroup.list();
            threadGroup.setDaemon(true);
            System.out.printf("Actual cuenta de hilos activos: %s%n",threadGroup.activeCount());
            threadOne.start();
            threadTwo.start();
            threadThree.start();
            Thread[] threadArray = new Thread[3];
            threadGroup.enumerate(threadArray);
            System.out.println("-----");
            List.of(threadArray)
                .forEach(System.out::println);
            System.out.println("-----");
            sleep(200);
            threadGroup.list();
            System.out.printf("Actual cuenta de hilos activos: %s%n",threadGroup.activeCount());
            threadGroup.interrupt();
            System.out.println(threadGroup.isDaemon());
            sleep(1000);
            new Thread(threadGroup,()-> heavyTask(2000),"Thread-2000").start();

      }

      private static void heavyTask(int millis)
      {
            var threadName = currentThread().getName();
            try
            {
                  sleep(millis);
                  System.out.printf("Se ha finalizado la tarea con éxito en el thread -- %s --%n",threadName);
            }
            catch (InterruptedException ex)
            {
                  System.out.printf("El thread -- %s --fue interrumpido%n",threadName);
            }
      }
}
