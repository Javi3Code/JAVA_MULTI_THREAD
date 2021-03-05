package org.jeycode.multithreadsamples.chapter2;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class VolatileOrAtomic
{

      /*
       * 
       * No usar volatile=>En una operación de modificación en la que una thread lee
       * -actualiza- y asigna el nuevo valor.
       * 
       * Java Atomic Types-> tipos mutables simples que proporcionan operaciones
       * básicas thread-safe y atomicas (volatile no) sin tener que bloquear (Bloques
       * o métodos synchronized).
       * 
       * Usarlos-> Cuando el bloqueo podría ser un cuello de botella para los thread o
       * haya riesgo de punto muerto o de bloqueo activo.
       * 
       */

//      public volatile long count;
      public AtomicLong count = new AtomicLong(0);

      public static void main(String[] args)
      {
            var volatileOrAtomic = new VolatileOrAtomic();
            var numThreads = 1000;
            var rounds = 1000;
            var deque = new ThreadPoolSample(numThreads,rounds,volatileOrAtomic).createThreadPoolReadAndWrite()
                                                                                .executeAllThreads()
                                                                                .awaitAllExecutions()
                                                                                .getResult();

            deque.stream()
                 .collect(Collectors.toMap(Function.identity(),value-> 1,(count,count2)-> count + count2))
                 .entrySet()
                 .stream()
                 .filter(key-> key.getValue() > 1)
                 .forEach(System.out::println);

            System.out.println("El resultado final es: " + volatileOrAtomic.count + "  y tenía que ser ->" + numThreads * rounds);

            System.out.println("Se ha realizado en " + deque.size() + " operaciones");

      }

}
