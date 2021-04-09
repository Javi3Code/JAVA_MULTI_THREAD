package org.jeycode.multithreadsamples.chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReadWriteLockApplication
{

      /*
       * Un ReadWriteLock mantiene un par de bloqueos asociados, uno de solo lectura y
       * otra para escritura. El bloqueo de lectura se puede mantener simultáneamente
       * para múltiples hilos de lectura, siempre que no haya escritores. El bloqueo
       * de escritura es exclusivo.
       */

      private static final String UPDATE_MSG = ":: está el escritor actualizando";
      private static final int NUM_THREADS = 10;
      static int round = 0;
      static boolean FINALIZED = false;
      private static final int ROUNDS = 100;
      private static final String ROUND_STR = "round:";
      private static final String SEPARATOR = "//";
      static StringBuilder anyStrB = new StringBuilder("-->");

      static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock(true);

      static Lock readLock = reentrantLock.readLock();
      static Lock writeLock = reentrantLock.writeLock();

      public static void main(String[] args)
      {
            
            Runnable readTask = ()->
                  {
                        var readerName = Thread.currentThread()
                                               .getName();
                        while (!FINALIZED)
                        {
                              readLock.lock();
                              try
                              {
                                    var nanoTime = System.nanoTime();
                                    System.out.println(readerName + SEPARATOR + nanoTime + SEPARATOR + anyStrB);
                              }
                              finally
                              {
                                    readLock.unlock();
                              }
                        }
                  };

            Runnable writeTask = ()->
                  {
                        var writerName = Thread.currentThread()
                                               .getName();
                        while (!FINALIZED)
                        {
                              writeLock.lock();
                              try
                              {
                                    var itsOdd = (round & 1) == 0;
                                    var strToAppend = itsOdd ? SEPARATOR + writerName + SEPARATOR : ROUND_STR + round;

                                    anyStrB.append(strToAppend);
                                    round++;
                                    int strLength = anyStrB.length();
                                    FINALIZED = strLength >= ROUNDS;
                                    System.out.println(strLength);
                              }
                              finally
                              {
                                    writeLock.unlock();
                              }
                        }
                        System.out.println("Final rounf[" + round + "::" + anyStrB + "]");
                  };

            JCThreadFactory.getExecutor(NUM_THREADS)
                           .withThis(readTask)
                           .andThis(writeTask)
                           .execute();

      }

      static class JCThreadFactory
      {

            private final Runnable readTask,writeTask;
            private final int numOfThreads;

            public JCThreadFactory(int numOfThreads,Runnable readTask,Runnable writeTask)
            {
                  this.numOfThreads = numOfThreads;
                  this.readTask = readTask;
                  this.writeTask = writeTask;
            }

            public static JCThreadFactoryBuilder getExecutor(int numOfThreads)
            {
                  return new JCThreadFactoryBuilder(numOfThreads);
            }

            public void execute()
            {
                  var lstOfTh = IntStream.range(0,numOfThreads)
                                         .mapToObj(num-> new Thread(readTask))
                                         .collect(Collectors.toList());
                  lstOfTh.add(0,new Thread(writeTask,"writer - 0"));
//                  lstOfTh.add(1,new Thread(writeTask,"writer - 1"));
                  lstOfTh.forEach(Thread::start);
            }

            static class JCThreadFactoryBuilder
            {

                  private final int numOfThreads;
                  private Runnable readTask;

                  public JCThreadFactoryBuilder(int numOfThreads)
                  {
                        this.numOfThreads = numOfThreads;
                  }

                  public JCThreadFactoryBuilder withThis(Runnable readTask)
                  {
                        this.readTask = readTask;

                        return this;
                  }

                  public JCThreadFactory andThis(Runnable writeTask)
                  {
                        return new JCThreadFactory(numOfThreads,readTask,writeTask);
                  }
            }

      }

      private static void sleep(int millis)
      {
            try
            {
                  Thread.sleep(millis);
            }
            catch (InterruptedException ex)
            {
                  ex.printStackTrace();
            }
      }

}
