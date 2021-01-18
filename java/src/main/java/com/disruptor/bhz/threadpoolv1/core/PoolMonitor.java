package com.disruptor.bhz.threadpoolv1.core;


import javax.management.MXBean;

@MXBean
public interface PoolMonitor {

    /**
     * 是否单一生产者
     *
     * @return
     */
    boolean isSingleProducer();

    /**
     * 连接器名称
     *
     * @return
     */
    String getConnectorName();

    /**
     * 连接器缓冲区大小
     *
     * @return
     */
    int getConnectorBufferSize();

    /**
     * 连接器最大空闲数
     *
     * @return
     */
    int getConnectorMaxRemainingCapacity();

    /**
     * 连接器最小空闲数
     *
     * @return
     */
    int getConnectorMinRemainingCapacity();

    /**
     * 接收器名称
     *
     * @return
     */
    String getAcceptorName();

    /**
     * 接收器缓冲区大小
     *
     * @return
     */
    int getAcceptorBufferSize();

    /**
     * 几个接收器
     *
     * @return
     */
    int getAcceptorSplitSize();

    /**
     * 接收器最大空闲
     *
     * @return
     */
    int[] getAcceptorsMaxRemainingCapacity();

    /**
     * 接收器最小空闲
     *
     * @return
     */
    int[] getAcceptorsMinRemainingCapacity();

    /**
     * 连接器当前空闲数
     *
     * @return
     */
    int getConnectorCurrentRemainingCapacity();

    /**
     * 接收器当前空闲数
     *
     * @return
     */
    int[] getAcceptorsCurrentRemainingCapacity();
}
