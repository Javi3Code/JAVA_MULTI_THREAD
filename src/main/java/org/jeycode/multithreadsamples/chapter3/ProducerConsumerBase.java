package org.jeycode.multithreadsamples.chapter3;

import java.util.Random;

public abstract class ProducerConsumerBase implements Runnable
{

      protected final Random random;
      protected String name;
      protected MailStorage mailStorage;
      protected final int effort;

      public ProducerConsumerBase(String name,MailStorage mailStorage,int effort)
      {
            this.name = name;
            this.mailStorage = mailStorage;
            this.effort = effort;
            this.random = new Random();
      }

      protected void simulateEffort()
      {
            try
            {
                  Thread.sleep(random.nextInt(effort));
            }
            catch (InterruptedException ex)
            {}
      }

}
