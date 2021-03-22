package org.jeycode.multithreadsamples.chapter4;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Getter;

public class MailStorage
{

      @Getter
      private final Queue<Mail> mailsQueue;
      private final int capacityLimit;
      @Getter
      private final AtomicBoolean workFinish = new AtomicBoolean(false);

      public MailStorage(int capacity)
      {
            this.capacityLimit = capacity;
            this.mailsQueue = new LinkedBlockingQueue<>(capacity);
      }

      public synchronized void sendMail(ConsumerAction<Mail> action)
      {
            if (mailsQueue.isEmpty() && !workFinish.get())
            {
                  try
                  {
                        wait();
                  }
                  catch (InterruptedException ex)
                  {}
            }
            var mail = mailsQueue.poll();
            if (mail != null)
            {
                  action.consume(mail);
                  notifyAll();
            }

      }

      public synchronized void storeMail(ProducerAction<Mail> action)
      {
            while (mailsQueue.size() == capacityLimit)
            {
                  try
                  {
                        wait();
                  }
                  catch (InterruptedException ex)
                  {}
            }

            mailsQueue.offer(action.produce());
            notifyAll();
      }
}
