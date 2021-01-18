package com.disruptor.bhz.threadpoolv1.core;

import com.disruptor.bhz.threadpoolv1.core.step.*;


import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ConnectorBuilder implements Step1, Step2, Step3, Step4, Step5, Step6, Step7, Step8 {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(ThreadFactorys.DAEMON_THREAD_FACTORY);

    private String connectorName;

    private boolean isSingleProducer;

    private int connectType;//1 directConnect 2 channelConnect(BufferConnector)

    private int connectBufferSize;

    private String acceptorName;

    private int acceptorBufferSize;

    private int acceptorSplitSize;

    private int acceptorPoolSize = 0;//0 不使用pool 大于0使用pool

    private int acceptorDistributorType;//1 sequenceDistributor 2 remainingDistributor

    private int actuatorType;//1 DirectActuator  2 SynchronousActuator

    private String actuatorName;

    private int actuatorSize;

    private boolean actuatorFair;

    private boolean canBuild = true;

    private ConnectorBuilder() {
    }

    @Override
    public synchronized Connector build() {
        check();
        canBuild = false;
        Connector connector = null;
        switch (connectType) {
            case 1:
                connector = new DirectConnector(connectorName, isSingleProducer);
                break;
            case 2:
                connector = new BufferConnector(connectorName, connectBufferSize, isSingleProducer);
                break;
        }
        Distributor distributor = null;
        switch (acceptorDistributorType) {
            case 1:
                distributor = new SequenceDistributor();
                break;
            case 2:
                distributor = new RemainingDistributor();
                break;
        }
        Acceptor acceptor = new Acceptor(acceptorName, acceptorBufferSize, acceptorSplitSize, isSingleProducer, distributor);
        if (actuatorType == 1) {
            DirectActuator actuator = new DirectActuator();
            if (acceptorPoolSize <= 1) {
                acceptor = acceptor.runWhith(actuator);
            } else {
                acceptor = acceptor.runWhithPool(actuator, acceptorPoolSize);
            }
        } else if (actuatorType == 2) {
            if (acceptorPoolSize <= 1) {
                SynchronousActuator[] actuators = new SynchronousActuator[acceptorSplitSize];
                for (int i = 0; i < acceptorSplitSize; i++) {
                    actuators[i] = new SynchronousActuator(actuatorName + "-" + (i + 1), actuatorSize, actuatorFair).start();
                }
                acceptor = acceptor.runWhith(actuators);
            } else {
                int size = acceptorSplitSize * acceptorPoolSize;
                SynchronousActuator[] actuators = new SynchronousActuator[size];
                for (int i = 0; i < size; i++) {
                    actuators[i] = new SynchronousActuator(actuatorName + "-" + (i + 1), actuatorSize, actuatorFair).start();
                }
                acceptor = acceptor.runWhithPool(actuators, acceptorPoolSize);
            }
        }
        connector = connector.connect(acceptor);
        try {
            TimedPoolMonitor timedPoolMonitor = new TimedPoolMonitor(isSingleProducer, connectorName, connectBufferSize, acceptorName, acceptorBufferSize, acceptorSplitSize);
            final AbsConnector absConnector = (AbsConnector) connector;
            final Acceptor finalAcceptor = acceptor;
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                int connectorRemainingCapacity = absConnector.getRemainingCapacity();
                timedPoolMonitor.updateConnectorRemainingCapacity(connectorRemainingCapacity);
                int[] acceptorRemainingCapacity = finalAcceptor.getRemainingCapacity();
                timedPoolMonitor.updateAcceptorsRemainingCapacity(acceptorRemainingCapacity);
            }, 1, 1, TimeUnit.SECONDS);
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("com.lou1052.core.base.thread.threadpool:type=PoolMonitor-" + timedPoolMonitor.hashCode());
            mBeanServer.registerMBean(timedPoolMonitor, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connector;
    }

    public static Step1 newBuilder() {
        return new ConnectorBuilder();
    }

    @Override
    public Step2 createConnector(String name) {
        check();
        this.connectorName = name;
        return this;
    }

    /**
     * 单个生产者（线程）
     *
     * @return
     */
    @Override
    public Step3 isSingleProducer() {
        check();
        this.isSingleProducer = true;
        return this;
    }

    /**
     * 多个生产者（线程）
     *
     * @return
     */
    @Override
    public Step3 isMultipleProducer() {
        check();
        this.isSingleProducer = false;
        return this;
    }

    @Override
    public Step3 isSingleProducer(boolean isSingleProducer) {
        check();
        this.isSingleProducer = isSingleProducer;
        return this;
    }

    /**
     * 直连
     *
     * @return
     */
    @Override
    public Step4 directConnect() {
        check();
        this.connectType = 1;
        return this;
    }

    /**
     * 缓冲连接
     *
     * @param bufferSize 缓冲大小
     * @return
     */
    @Override
    public Step4 channelConnect(int bufferSize) {
        check();
        this.connectType = 2;
        this.connectBufferSize = bufferSize;
        return this;
    }

    /**
     * 连接接收器
     *
     * @param name       接收器名称
     * @param bufferSize 缓冲大小
     * @param splitSize  几个接收器
     * @return
     */
    @Override
    public Step5 connectAcceptor(String name, int bufferSize, int splitSize) {
        check();
        this.acceptorName = name;
        this.acceptorBufferSize = bufferSize;
        this.acceptorSplitSize = splitSize;
        return this;
    }

    /**
     * 使用 顺序选择器
     *
     * @return
     */
    @Override
    public Step6 sequenceDistributor() {
        check();
        this.acceptorDistributorType = 1;
        return this;
    }

    /**
     * 使用 空闲选择器
     *
     * @return
     */
    @Deprecated //效率低下 阻塞connector
    @Override
    public Step6 remainingDistributor() {
        check();
        this.acceptorDistributorType = 2;
        return this;
    }

    /**
     * 使用直接执行器
     *
     * @return
     */
    @Override
    public Step7 runWhithDirectActuator() {
        check();
        this.actuatorType = 1;
        return this;
    }

    /**
     * 使用同步执行器
     *
     * @param name 线程名称
     * @param size 线程个数
     * @param fair 公平非公平执行
     * @return
     */
    @Override
    public Step7 runWhithSynchronousActuator(String name, int size, boolean fair) {
        check();
        this.actuatorType = 2;
        this.actuatorName = name;
        this.actuatorSize = size;
        this.actuatorFair = fair;
        return this;
    }

    /**
     * disruptor.handleEventsWithWorkerPool
     *
     * @param poolSize
     * @return
     */
    @Override
    public Step8 usePool(int poolSize) {
        check();
        this.acceptorPoolSize = poolSize;
        return this;
    }

    private void check() {
        if (!canBuild) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConnectorBuilder{");
        sb.append("connectorName='").append(connectorName).append('\'');
        sb.append(", isSingleProducer=").append(isSingleProducer);
        sb.append(", connectType=").append(connectType);
        sb.append(", connectBufferSize=").append(connectBufferSize);
        sb.append(", acceptorName='").append(acceptorName).append('\'');
        sb.append(", acceptorBufferSize=").append(acceptorBufferSize);
        sb.append(", acceptorSplitSize=").append(acceptorSplitSize);
        sb.append(", acceptorPoolSize=").append(acceptorPoolSize);
        sb.append(", acceptorDistributorType=").append(acceptorDistributorType);
        sb.append(", actuatorType=").append(actuatorType);
        sb.append(", actuatorName='").append(actuatorName).append('\'');
        sb.append(", actuatorSize=").append(actuatorSize);
        sb.append(", actuatorFair=").append(actuatorFair);
        sb.append('}');
        return sb.toString();
    }
}
