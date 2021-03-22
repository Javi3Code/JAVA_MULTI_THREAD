package org.jeycode.multithreadsamples.chapter4;

@FunctionalInterface
public interface ProducerAction<Producible>
{

      Producible produce();
}
