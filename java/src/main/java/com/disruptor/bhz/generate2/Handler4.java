package com.disruptor.bhz.generate2;

import com.disruptor.bhz.generate1.Trade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler4 implements EventHandler<Trade>,WorkHandler<Trade> {
	  
    @Override  
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {

       // System.out.println("gzh " + sequence+" end  "+ endOfBatch);
        this.onEvent(event);  
    }  
  
    @Override  
    public void onEvent(Trade event) throws Exception {  
    	//System.out.println("handler4: get name : " + event.getName());
    	event.setName(event.getName() + "h222");
    }  
}  