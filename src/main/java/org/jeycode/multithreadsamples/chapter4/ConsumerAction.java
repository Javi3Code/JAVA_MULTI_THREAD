package org.jeycode.multithreadsamples.chapter4;

@FunctionalInterface
public interface ConsumerAction<Consumable>
{

      void consume(Consumable obj);
}
