package com.epam.port.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Dock {

    private Integer dockNumber;
    private boolean free = true;
    private Semaphore semaphore;
    private List<Container> containerList = new ArrayList<>();

    public Dock(Integer dockNumber) {
        this.dockNumber = dockNumber;
    }

    ReentrantLock containerLock = new ReentrantLock();

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public boolean isFree() {
        return free;
    }

    public void isBusy() {
        free = false;
    }

    public void release() {
        free = true;
        semaphore.release();
    }

    public List<Container> getReadyContainers() {
        return containerList;
    }

    public Integer getDockNumber() {
        return dockNumber;
    }

    public void addForWait(Container container) {
        containerLock.lock();
        containerList.add(container);
        containerLock.unlock();
    }

    public void removeFromWait(Container container) {
        containerLock.lock();
        containerList.remove(container);
        containerLock.unlock();
    }
}
