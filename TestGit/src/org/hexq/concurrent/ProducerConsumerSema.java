package org.hexq.concurrent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/*
 * main program that tests the multiple producers and multiple consumers running in parallel.
 */
public class ProducerConsumerSema
{
    // the shared buffer for both producers and consumers
    // we suppose one product is a string.
    public static List<String> sharedBuffer = new ArrayList<String>();
    
    // create 2 semaphores for the buffer full and buffer empty
    // if buffer is full, all producers should wait; 
    // if buffer is empty, all consumers should wait.
    public static Semaphore semaphoreBufFull = new Semaphore(5);
    public static Semaphore semaphoreBufEmpty = new Semaphore(0);
    
    public static void main(String[] args)
    {
        try
        {
            // create and start 2 producers thread
            for ( int i=0; i<2; i++ )
            {
                Producer producer = new Producer(i);
                producer.start();
                Thread.sleep( 1000 );
            }
            
            // wait for 5 seconds
            Thread.sleep( 5000 );
            
            // create and start 3 consumers thread
            for ( int i=0; i<3; i++ )
            {
                Consumer consumer = new Consumer(i);
                consumer.start();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

/*
 * This is the producer class that extends Thread.
 */
class Producer extends Thread
{
    private int id;
    
    public Producer( int id )
    {
        this.id = id;
    }
    
    public void run()
    {
        while (true)
        {
            // create a new product
            Calendar cal = Calendar.getInstance();
            Random rand =  new Random(cal.getTimeInMillis());
            String product = "NewProduct " + rand.nextInt(20);
            
            // try to acquire a semaphore if the buffer is not full
            try
            {
                ProducerConsumerSema.semaphoreBufFull.acquire();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }

            // this producer thread is accessing the shared buffer...
            List<String> buffer = ProducerConsumerSema.sharedBuffer;
            synchronized (buffer)
            {
                buffer.add( product );
                System.out.println("Producer " + id + " produced one new product:" + product +"! Buffer size=" + buffer.size() + "!");
            }
            
            // now we have produced a new product, let"s wake up any waiting consumers
            ProducerConsumerSema.semaphoreBufEmpty.release();
            
            try
            {
                Thread.sleep( 1000 ); //1s
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}

/*
 * This is the consumer class that extends Thread.
 */
class Consumer extends Thread
{
    private int id;
    
    public Consumer( int id )
    {
        this.id = id;
    }
    
    public void run()
    {
        String product = null;

        while (true)
        {
            // try to acquire a semaphore if the buffer is not empty
            try
            {
                ProducerConsumerSema.semaphoreBufEmpty.acquire();
            }
            catch (InterruptedException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            // this consumer thread is accessing the shared buffer...
            List<String> buffer = ProducerConsumerSema.sharedBuffer;
            synchronized (buffer)
            {
                int count = buffer.size();
                product = buffer.get( count-1 );
                buffer.remove( count-1 );
                System.out.println("Consumer " + id + " consumed one product:" + product +"!");
            }
            
            // now we have consumed a product, let"s wake up any waiting producers
            ProducerConsumerSema.semaphoreBufFull.release();
            
            try
            {
                Thread.sleep( 1000 );
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
