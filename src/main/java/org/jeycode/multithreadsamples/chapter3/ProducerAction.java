package org.jeycode.multithreadsamples.chapter3;

@FunctionalInterface
public interface ProducerAction<Producible>
{

      Producible produce();
}
