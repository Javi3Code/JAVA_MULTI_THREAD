package org.jeycode.multithreadsamples.helpers;

import static java.util.concurrent.TimeUnit.*;

import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Sleep
{

      public static final void nanos(int timeout)
      {
            sleep(NANOSECONDS,timeout);
      }

      public static final void micros(int timeout)
      {
            sleep(MICROSECONDS,timeout);
      }

      public static final void millis(int timeout)
      {
            sleep(MILLISECONDS,timeout);
      }

      public static final void seconds(int timeout)
      {
            sleep(SECONDS,timeout);
      }

      public static final void minutes(int timeout)
      {
            sleep(MINUTES,timeout);
      }

      public static final void hours(int timeout)
      {
            sleep(HOURS,timeout);
      }

      public static final void days(int timeout)
      {
            sleep(DAYS,timeout);
      }

      private static final void sleep(TimeUnit unit,int timeout)
      {
            try
            {
                  unit.sleep(timeout);
            }
            catch (InterruptedException ex)
            {
                  log.error("Sleep Helper Method Interrupted",ex);
            }
      }
}
