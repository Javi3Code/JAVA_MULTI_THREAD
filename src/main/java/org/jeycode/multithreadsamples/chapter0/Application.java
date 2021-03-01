package org.jeycode.multithreadsamples.chapter0;

import java.util.stream.LongStream;

public final class Application
{

      public static void main(String[] args)
      {
            // Diferente formas de crear un Thread

            var threadThatInherits = new ExampleOfThread("//Hereda de thread e implementa el método run");
            var threadThatImplements = new Thread(new OtherExampleOfThread(),"//Utiliza un Runnable externo");
            var threadUsingLambdaExpression = new Thread(Application::printSum,"//Utiliza Runnable usando lambda");
            threadThatInherits.start();
            threadThatImplements.start();
            threadUsingLambdaExpression.start();
      }

      private static void printSum()
      {
            System.out.println("Ha finalizado la suma del hilo " + Thread.currentThread()
                                                                         .getName()
                                    + ": " + LongStream.rangeClosed(0,10000000)
                                                       .sum());
      }

      public static class ExampleOfThread extends Thread
      {

            public ExampleOfThread(String name)
            {
                  setName(name);
            }

            @Override
            public void run()
            {
                  System.out.println("Ha finalizado la suma del hilo " + getName() + ": " + LongStream.rangeClosed(0,10000000)
                                                                                                      .sum());
            }
      }

      public static class OtherExampleOfThread implements Runnable
      {

            @Override
            public void run()
            {
                  System.out.println("Ha finalizado la suma del hilo " + Thread.currentThread()
                                                                               .getName()
                                          + ": " + LongStream.rangeClosed(0,10000000)
                                                             .sum());

            }

      }

}
