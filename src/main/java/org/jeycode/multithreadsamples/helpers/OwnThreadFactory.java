package org.jeycode.multithreadsamples.helpers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OwnThreadFactory implements ThreadFactory
{

      private static final AtomicInteger poolNumber = new AtomicInteger(1);
      private final ThreadGroup group;
      private final AtomicInteger threadNumber = new AtomicInteger(1);
      private final String namePrefix;

      public OwnThreadFactory()
      {
            SecurityManager s = System.getSecurityManager();
            group = s != null ? s.getThreadGroup() : Thread.currentThread()
                                                           .getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
      }

      @Override
      public Thread newThread(Runnable r)
      {
            Thread t = new Thread(group,r,namePrefix + threadNumber.getAndIncrement(),0);
            if (t.isDaemon())
            {
                  t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY)
            {
                  t.setPriority(Thread.NORM_PRIORITY);
            }
            log.info("\t\tSe pide nuevo thread");
            return t;
      }

}
