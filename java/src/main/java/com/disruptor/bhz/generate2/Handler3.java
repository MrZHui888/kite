package com.disruptor.bhz.generate2;


import com.disruptor.bhz.generate1.Trade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler3 implements  EventHandler<Trade>, WorkHandler<Trade> {
    @Override  
    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {  
    	//System.out.println(Thread.currentThread().getId()+"  "+"handler3: name: " + event.getName() + " , price: " + event.getPrice() + ";  instance: " + event.toString()+"sequence :"+sequence);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {

    }
}
