package com.disruptor.bhz.generate2;

import com.disruptor.bhz.generate1.Trade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;


public class Handler6 implements EventHandler<Trade>, WorkHandler<Trade> {
	  
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }


    @Override
    public void onEvent(Trade event) throws Exception {  
    	//System.out.println(Thread.currentThread().getId()+"  "+"handler6: ");
    }
}  