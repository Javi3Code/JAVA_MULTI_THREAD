package org.jeycode.multithreadsamples.chapter6;

import static java.lang.Thread.*;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import static java.lang.String.*;

public class ThreadLocalSamples
{

      static final ConcurrentLinkedDeque<ContextObj> setOfAnyObj = new ConcurrentLinkedDeque<>();
      static final String THREAD_ACTUAL_VALUE_STR = "Hilo [%s] // Valor actual [%s]\n";
//      static final ThreadLocal<ContextObj> contextContainer = ThreadLocal.withInitial(ContextObj::new);
      static final OwnInheritableThreadLocal contextContainer = new OwnInheritableThreadLocal();

      static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,3,TimeUnit.SECONDS,new LinkedBlockingQueue<>());
      public static final String MAIN_T_NAME = currentThread().getName();

      public static void main(String[] args)
      {
            contextContainer.set(new ContextObj());
            var threadSimple = new Thread(()->
                  {
                        task();
                  },"threadSimple");
            var threadComplex = new Thread(()->
                  {
                        task();
                        new Thread(ThreadLocalSamples::task,"threadComplexChild").start();
                  },"threadComplex");

            final var contextObj = contextContainer.get();
            System.out.println(format(THREAD_ACTUAL_VALUE_STR,MAIN_T_NAME,contextObj));
//            List.of(threadSimple,threadComplex)
//                .forEach(Thread::start);

            executor.submit(threadSimple);
            executor.submit(threadComplex);
            put(contextObj);
            trySleep(500);
            System.out.println(format(THREAD_ACTUAL_VALUE_STR,MAIN_T_NAME,contextObj));
            executor.submit(ThreadLocalSamples::task);

            put(contextObj);
            trySleep(500);
            System.out.println(format(THREAD_ACTUAL_VALUE_STR,MAIN_T_NAME,contextObj));

            executor.shutdown();

            final var setCollect = setOfAnyObj.stream()
                                              .peek(System.out::println)
                                              .collect(Collectors.toSet());
            System.out.println();
            setCollect.forEach(System.out::println);

      }

      public static synchronized void task()
      {
            var value = contextContainer.get();
            put(value);
            final var threadName = currentThread().getName();
            if (value.getSource() == null)
            {
                  System.out.println(format("Thread[%s] entra a cambiar source",threadName));
                  value.setSource(threadName);
            }
            else
            {
                  System.out.println(format("Thread[%s] entra a cambiar id",threadName));
                  value.setId(value.getId() + 1);
            }
            System.out.println(format(THREAD_ACTUAL_VALUE_STR,threadName,contextContainer.get()));
            contextContainer.remove();
      }

      private static void put(ContextObj obj)
      {
            setOfAnyObj.add(obj);
      }

      public static class OwnInheritableThreadLocal extends InheritableThreadLocal<ContextObj>
      {

            @Override
            protected ContextObj initialValue()
            {
                  System.out.println(format("[Thread:%s] Al no existir un valor inicial recurre a este método",currentThread().getName()));
                  return new ContextObj();
            }

            @Override
            protected ContextObj childValue(ContextObj parentValue)
            {
                  System.out.println(format("Thread[%s]:::El valor del padre era [%s]",currentThread().getName(),parentValue));
                  return parentValue;
            }
      }

      @Data
      @RequiredArgsConstructor
      public static class ContextObj
      {

            private int id;
            private String source;
      }

      public static void trySleep(long millis)
      {
            try
            {
                  sleep(millis);
            }
            catch (InterruptedException ex)
            {}
      }
}
