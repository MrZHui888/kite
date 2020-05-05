package com.disruptor.bhz.generate2;


import com.disruptor.bhz.generate1.Trade;
import com.lmax.disruptor.EventHandler;

public class Handler2 implements EventHandler<Trade> {
	  
    @Override  
    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {  
    	//System.out.println(Thread.currentThread().getId()+" "+"handler2: set price");
    	event.setPrice(17.0);
    }
      
}  