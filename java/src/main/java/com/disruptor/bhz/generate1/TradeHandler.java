package com.disruptor.bhz.generate1;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;


public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        //这里做具体的消费逻辑
        String uuid = UUID.randomUUID().toString();
        event.setId(uuid);//简单生成下ID
        System.out.println(this.getClass().getSimpleName() + "事件id   " + sequence + " 生成的id为:  " + uuid +" 金额:  "+event.getPrice() );

        this.onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws Exception {
//        //这里做具体的消费逻辑
//        String uuid = UUID.randomUUID().toString();
//        event.setId(uuid);//简单生成下ID
//        System.out.println(this.getClass().getSimpleName() + "事件id   " + event.getId() + " 生成的id为:  " + uuid);
    }
}  