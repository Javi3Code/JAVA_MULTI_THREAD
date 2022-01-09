package org.jeycode.multithreadsamples.chapter3;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.javafaker.Faker;


public class MailsProducer extends ProducerConsumerBase
{

      private static final String EMPTY = "";
      private static final String SPACE = " ";
      private final Faker faker = new Faker();
      ProducerAction<Mail> action;

      public static AtomicInteger numberOfMailsToSends;

      public MailsProducer(String name,MailStorage mailStorage)
      {
            super(name,mailStorage,100);
            action = ()->
                  {
                        var mail = createMail();
                        System.out.println("\n<<" + name + " coloca en la pila de correos el siguiente mail-> " + mail + ">>");
                        return mail;
                  };
      }

      @Override
      public void run()
      {
            while (!(numberOfMailsToSends.decrementAndGet() < 0))
            {
                  simulateEffort();
                  mailStorage.storeMail(action);
            }
            mailStorage.getWorkFinish()
                       .compareAndSet(false,true);
      }

      private Mail createMail()
      {
            return new Mail(faker.howIMetYourMother()
                                 .catchPhrase(),
                            faker.name()
                                 .name(),
                            faker.name()
                                 .name()
                                 .replaceAll(SPACE,EMPTY) + ".@gmail.com");
      }

}
