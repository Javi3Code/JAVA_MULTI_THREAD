package org.jeycode.multithreadsamples.chapter1;

public class VolatileSample
{

      /*
       * Usando la palabra reservada volatile obligamos a los hilos a leer su valor de
       * la mem principal y no del caché de la CPU, de esta forma garantizamos que el
       * valor leído siempre es el último valor escrito en ellas.
       * 
       * NO NOS ASEGURA QUE LAS OPERACIONES SOBRE ESTAS SEAN ATÓMICAS.
       * 
       * Tan pronto como un hilo necesita leer primero el valor de una variable
       * volatile, y en base a ese valor generar un nuevo valor para la variable
       * volatile compartida , una variable volatile ya no es suficiente para
       * garantizar una visibilidad correcta.
       * 
       * El breve intervalo de tiempo entre la lectura de la volatile variable y la
       * escritura de su nuevo valor, crea una condición de carrera en la que varios
       * subprocesos pueden leer el mismo valor de la variable volatile, generar un
       * nuevo valor para la variable y al escribir el valor de nuevo en memoria
       * principal: sobrescriben los valores de cada uno.
       * 
       * El acceso a variables volátiles también evita el reordenamiento de las
       * instrucciones, que es una técnica normal de mejora del rendimiento. Por lo
       * tanto, solo debe usar variables volátiles cuando realmente necesite hacer
       * cumplir la visibilidad de las variables -->Afecta a la performance. Lectura y
       * escritura mucho más lentas.
       * 
       * 
       */
      public static int number;
      public static volatile boolean stop;
      public static boolean increment;
      private static boolean valueOfStopInReader;

      public static void main(String[] args) throws InterruptedException
      {
            var reader = new Thread(getValueReader());
            reader.start();
            var writer = new Thread(getValueWriter());
            writer.start();
            Thread.sleep(1000);
            System.out.println(valueOfStopInReader);

      }

      private static Runnable getValueWriter()
      {
            return ()->
                  {
                        var rounds = 0;
                        while (!stop)
                        {
                              number += increment ? +1 : -1;
                              if (number == 10 || number == -10)
                              {
                                    increment = !increment;
                                    rounds++;
                                    stop = rounds == 100000;
                              }
                        }
                        System.out.println("Hilo de escritura ->" + number);
                  };
      }

      private static Runnable getValueReader()
      {
            return ()->
                  {
                        while (!stop)
                        {
                              valueOfStopInReader = stop;
                        }
                        System.out.println("Se acabó el trabajo || lectura/::" + number + "||");
                  };
      }

}
