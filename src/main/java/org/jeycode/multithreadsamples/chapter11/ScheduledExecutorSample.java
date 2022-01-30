package org.jeycode.multithreadsamples.chapter11;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.Executors.*;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.atomic.AtomicReference;

import org.jeycode.multithreadsamples.helpers.Sleep;

public class ScheduledExecutorSample
{

      private static final String MSG_FORMAT = "\nMethodKey {%s} :: Time on: {%s} :: Time off: {%s}";
      private static final String SCHEDULE_METHOD = "schedule method";
      private static final String SCHEDULE_AT_METHOD = "scheduleAtFixedRate method";
      private static final String SCHEDULE_FIX_METHOD = "scheduleWithFixedDelay method";
      static AtomicReference<String> story = new AtomicReference<>("Comenzamos...");
      static Long on;

      public static void main(String[] args)
      {
            on = currentTimeMillis();
//            final var scheduleExecutor= newSingleThreadScheduledExecutor();
            final var scheduleExecutor = newScheduledThreadPool(3);
            scheduleExecutor.schedule(()-> append(SCHEDULE_METHOD),3,SECONDS);
            scheduleExecutor.scheduleAtFixedRate(()-> append(SCHEDULE_AT_METHOD),0,1,SECONDS);
            scheduleExecutor.scheduleWithFixedDelay(()-> append(SCHEDULE_FIX_METHOD),0,1,SECONDS);
            Sleep.seconds(20);
            scheduleExecutor.shutdown();
            System.out.println(story.getPlain());

      }

      private static void append(String methodKey)
      {
            final var onDiff = (currentTimeMillis() - on) / 1000;
            Sleep.seconds(1);
            story.accumulateAndGet(format(MSG_FORMAT,methodKey,onDiff,(currentTimeMillis() - on) / 1000),String::concat);
      }

}
