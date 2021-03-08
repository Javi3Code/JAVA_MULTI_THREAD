package org.jeycode.multithreadsamples.chapter3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ApplicationProducerConsumerSample
{

      // Monitor => objeto destinado a ser utilizado de forma segura por más de un
      // hilo

      // Producer-Consumer => queremos procesar los datos en el orden en el que fueron
      // obtenidos.

      // Cola de mails -> enviados en el mismo orden que se obtuvieron

      private static final String CONSUMER_NAME_OPEN = "Consumer<";
      private static final String PRODUCER_NAME_OPEN = "Producer <";
      private static final String NAME_CLOSE = ">";

      public static void main(String[] args)
      {
            var mailStorage = new MailStorage(100);

            var numOfMails = 20000;
            MailsProducer.numberOfMailsToSends = new AtomicInteger(numOfMails);

            IntStream.range(0,200)
                     .mapToObj(num-> new Thread(new MailsProducer(PRODUCER_NAME_OPEN + num + NAME_CLOSE,mailStorage)))
                     .forEach(Thread::start);

            var lstOfConsumers = IntStream.range(0,100)
                                          .mapToObj(num-> new Thread(new MailsConsumer(CONSUMER_NAME_OPEN + num + NAME_CLOSE,mailStorage,
                                                                                       numOfMails)))
                                          .collect(Collectors.toUnmodifiableList());

            lstOfConsumers.forEach(Thread::start);

            lstOfConsumers.forEach(await());

            System.out.println("Total de mails enviados: " + MailsConsumer.allMailsSent.size());

      }

      private static Consumer<? super Thread> await()
      {
            return thread->
                  {
                        try
                        {
                              thread.join();
                        }
                        catch (InterruptedException ex)
                        {}
                  };
      }

}
