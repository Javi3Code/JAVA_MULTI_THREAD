package org.jeycode.multithreadsamples.chapter3;

@FunctionalInterface
public interface ConsumerAction<Consumable>
{

      void consume(Consumable obj);
}
