package com.epam.port.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Ship extends Thread {

    private final static Logger Log = LoggerFactory.getLogger(Ship.class);

    private Integer shipNumber;
    private List<Container> containers;
    private Port port;
    //private boolean free = true;
    //private Semaphore semaphore1;

//    public void setSemaphore(Semaphore semaphore) {
//        this.semaphore1 = semaphore;
//    }

    //  ReentrantLock lock = new ReentrantLock();

    public Ship(Integer shipNumber, List<Container> containers, Port port) {
        this.shipNumber = shipNumber;
        this.containers = containers;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            //sleep((long)(Math.random()*500));
            Dock dock = port.getDock();
            Log.info("Ship number {} went to dock number {}", getShipNumber(), dock.getDockNumber());
            releaseContainers(dock);

            prepareToTransport(dock);

            transport(dock);

            Log.info("Ship number {} left dock number {}", getShipNumber(), dock.getDockNumber());
        } catch (InterruptedException e) {
            interrupt();
            Log.error("Ship have been crashed", e);
            throw new RuntimeException();
        }
    }

    private void transport(Dock dock) {
        containers.forEach(container -> {
            container.inShip();
            Log.info("Ship number {} has container {}", getShipNumber(), container.getNumber());
        });
        dock.release();
    }

    private void releaseContainers(Dock dock) throws InterruptedException {
        //port.containerLoading(containers);

        containers.forEach(Thread::start);
        containers.clear();
        Log.info("Ship number {} released containers", getShipNumber());
        sleep((long) (100 + Math.random() * 300));
    }

    private void prepareToTransport(Dock dock) throws InterruptedException {
        List<Container> readyContainers = dock.getReadyContainers();
        sleep((long) (100 + Math.random() * 300));
        readyContainers.forEach(p -> containers.add(p));
        readyContainers.clear();
        Log.info("Ship number {} loaded by containers", getShipNumber());
    }

    public Integer getShipNumber() {
        return shipNumber;
    }

    public boolean isFree() {
        return true;
    }
/*
    public void isBusy() {
        free = false;
    }

    public void release() {
        free = true;
        semaphore1.release();
    }

    public void addForWait(Container container, Date timeExpiration) {
        lock.lock();
        containers.add(container);
        lock.unlock();
    }

    public void removeFromWait(Container container) {
        lock.lock();
        containers.remove(container);
        lock.unlock();
    }
*/

}
