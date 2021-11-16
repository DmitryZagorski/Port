package com.epam.port.models;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Port {

    private List<Dock> docks;
    //private List<Ship> ships;
    // private List<Container> repository = new ArrayList<>();

    private Semaphore semaphore;
    //private Semaphore semaphore1;
    private ReentrantLock lock = new ReentrantLock();


    public Port(List<Dock> docks/*, List<Ship> ships*/) {
        this.docks = docks;
        semaphore = new Semaphore(docks.size());
        docks.forEach(t -> t.setSemaphore(semaphore));

        // semaphore1 = new Semaphore(ships.size());
    }

    public Dock getDock() throws InterruptedException {
        semaphore.acquire();
        lock.lock();
        Dock freeDock = docks.stream().filter(a -> a.isFree()).findFirst().get();
        freeDock.isBusy();
        lock.unlock();
        return freeDock;
    }

  /*  public Ship getShip() throws InterruptedException {

        //semaphore1.acquire();
        semaphore.acquire();
        Ship freeShip = ships.stream().filter(b -> b.isFree()).findFirst().get();
        freeShip.setSemaphore(semaphore);
        lock.lock();
        freeShip.isBusy();
        lock.unlock();
        return freeShip;
    } */

    public Dock getDockByNumber(int number) {
        return docks.get(number - 1);
    }

}
